package com.perpule.roomService;

import com.perpule.models.CallDetailsModel;
import com.perpule.singletons.DBManager;
import com.perpule.utils.RandomAndHashStringUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

	public RoomDatabaseModel createRoom(RoomDatabaseModel roomDatabaseModel)
			throws SQLException, NoSuchAlgorithmException {
		String createdAt = String.valueOf(System.currentTimeMillis() / 1000L);
		String requestedTime = createdAt;
		roomDatabaseModel.setRequestedTime(Integer.parseInt(requestedTime));
		String deviceId = roomDatabaseModel.getDeviceId();
		String id = RandomAndHashStringUtil.getId(deviceId, createdAt);
		String averageWaitingTime = String.valueOf(getAverageWaitingTime());
		String consumerId = "NULL";
		roomDatabaseModel.setId(id);
		roomDatabaseModel.setAverageWaitingTime(Integer.parseInt(averageWaitingTime));
		if (roomDatabaseModel.getConsumerId() != null) {
			consumerId = "'" + roomDatabaseModel.getConsumerId() + "'";
		}
		String sqlQuery = "INSERT INTO `room` (`id`, `consumerId`, `deviceId`, `requestedTime`, `startTime`, `averageWaitingTime`, `endTime`, `salesmanId`) VALUES ( '"
				+ id + "' , " + consumerId + " , '" + deviceId + "' , '" + requestedTime + "' , NULL , '"
				+ averageWaitingTime + "', NULL , NULL )";
		if (DBManager.doQuery(sqlQuery)) {
			return roomDatabaseModel;
		} else {
			throw new Error("doQuery function not working!");
		}
	}

	public RoomDatabaseModel updateEndTimeOfRoom(RoomDatabaseModel roomDatabaseModel) throws SQLException {

		String endTime = String.valueOf(System.currentTimeMillis() / 1000L);
		roomDatabaseModel.setEndTime(Integer.parseInt(endTime));
		String sqlQuery = "UPDATE room set endTime='" + endTime + "' where id='" + roomDatabaseModel.getId()+"'";
		

		if (DBManager.doQuery(sqlQuery)) {
			return roomDatabaseModel;
		} else {
			throw new Error("doQuery function not working!");
		}

	}

	public RoomDatabaseModel ifRequestedRoomAlreadyThereForThisDevice(RoomDatabaseModel roomDatabaseModel)
			throws SQLException {

		String sqlQuery = "SELECT * FROM `room` WHERE salesmanId IS NULL AND deviceId = '"
				+ roomDatabaseModel.getDeviceId() + "'";
		ResultSet resultSet = DBManager.getResultset(sqlQuery);
		if (resultSet.isBeforeFirst()) {
			resultSet.next();
			roomDatabaseModel.setId(resultSet.getString("id"));
			roomDatabaseModel.setAverageWaitingTime(Integer.parseInt(resultSet.getString("averageWaitingTime")));
		}
		return roomDatabaseModel;
	}

	public int getAverageWaitingTime() throws SQLException {
		String sqlQuery = "SELECT AVG(`waitingTime`) AS `averageWaitingTime` FROM (SELECT `startTime` - `requestedTime` AS `waitingTime` FROM `room`) AS waitingTimeTable";
		ResultSet resultSet = DBManager.getResultset(sqlQuery);
		if (resultSet.isBeforeFirst()) {
			resultSet.next();
			if (resultSet.getInt("averageWaitingTime") == 0) {
				return 10;
			} else {
				return resultSet.getInt("averageWaitingTime");
			}
		} else {
			return 10;
		}
	}

	public RoomDatabaseModel getEmptyRequestedRoom(String salesmanId) throws SQLException {
		RoomDatabaseModel roomDatabaseModel = new RoomDatabaseModel();
		String sqlQuery = "SELECT * FROM `room` WHERE salesmanId IS NULL ORDER BY `room`.`requestedTime` ASC";
		ResultSet resultSet = DBManager.getResultset(sqlQuery);
		if (resultSet.isBeforeFirst()) {
			resultSet.next();
			sqlQuery = "UPDATE `salesman` SET isOccupied = TRUE WHERE id = '" + salesmanId + "'";
			String createdAt = String.valueOf(System.currentTimeMillis() / 1000L);
			if (DBManager.doQuery(sqlQuery)) {
				sqlQuery = "UPDATE `room` SET salesmanId = '" + salesmanId + "' , startTime = " + createdAt
						+ " WHERE id = '" + resultSet.getString("id") + "' ";
				if (DBManager.doQuery(sqlQuery)) {
					roomDatabaseModel.setId(resultSet.getString("id"));
					return roomDatabaseModel;
				} else {
					return roomDatabaseModel;
				}
			} else {
				return roomDatabaseModel;
			}
		} else {
			return roomDatabaseModel;
		}
	}
	
	public List<CallDetailsModel> getCallDetails() throws SQLException {
		List<CallDetailsModel> callDetailsModelList = new ArrayList<CallDetailsModel>();
		String reportQuery = "select b.name as Customer,b.phoneNumber CustomerMobileNumber,b.productName as ProductSearchedFor,c.userName as SalesMan,"
				+ " from_unixtime(startTime) as StartTime,from_unixtime(endTime) as EndTime,(endTime - startTime)  as Duration from room a "
				+ " inner join consumer b on (a.consumerId=b.id) inner join salesman c on (a.salesmanId = c.id)";
		ResultSet resultSet = DBManager.getResultset(reportQuery);
		while (resultSet.next()) {
			CallDetailsModel callDetailsModel = new CallDetailsModel();
			callDetailsModel.setCustomerName(resultSet.getString("Customer"));
			callDetailsModel.setCustomerPhoneNumber(resultSet.getString("CustomerMobileNumber"));
			callDetailsModel.setProductSearchedFor(resultSet.getString("ProductSearchedFor"));
			callDetailsModel.setSalesman(resultSet.getString("SalesMan"));
			callDetailsModel.setStartTime(resultSet.getString("StartTime"));
			callDetailsModel.setEndTime(resultSet.getString("EndTime"));
			callDetailsModel.setDuration(resultSet.getString("Duration"));
			callDetailsModelList.add(callDetailsModel);
		}
		return callDetailsModelList;
	}

	public boolean isSalesmanAllotted(String roomId) throws SQLException {
		String sqlQuery = "SELECT * FROM `room` WHERE salesmanId IS NOT NULL AND id = '" + roomId + "'";
		ResultSet resultSet = DBManager.getResultset(sqlQuery);
		return resultSet.isBeforeFirst();
	}

	public boolean deleteRoom(String roomId) throws SQLException {
		String sqlQuery = "DELETE FROM `room` WHERE salesmanId IS NULL AND id = '" + roomId + "'";
		return DBManager.update(sqlQuery) > 0;
	}
}

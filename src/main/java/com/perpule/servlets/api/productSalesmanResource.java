// package com.perpule.servlets.api;
//
// import com.google.gson.Gson;
// import com.perpule.constants.ResponseCodeConstant;
// import com.perpule.models.ResponseModel;
// import com.perpule.productSalesmanService.ProductSalesmanDAO;
// import com.perpule.productSalesmanService.ProductSalesmanDatabaseRequestModel;
// import com.perpule.salesmanService.SalesmanDAO;
//
// import javax.ws.rs.Consumes;
// import javax.ws.rs.POST;
// import javax.ws.rs.Path;
// import javax.ws.rs.Produces;
// import javax.ws.rs.core.Context;
// import javax.ws.rs.core.HttpHeaders;
// import javax.ws.rs.core.MediaType;
// import java.security.NoSuchAlgorithmException;
// import java.sql.SQLException;
//
// @Path("productSalesman")
// public class productSalesmanResource {
//
//   private SalesmanDAO salesmanDAO = new SalesmanDAO();
//   private ProductSalesmanDAO productSalesmanDAO = new ProductSalesmanDAO();
//
//   @Path("createProductSalesman")
//   @POST
//   @Consumes({MediaType.APPLICATION_JSON})
//   @Produces(MediaType.APPLICATION_JSON)
//   public ResponseModel createProduct(
//       ProductSalesmanDatabaseRequestModel productSalesmanDatabaseRequestModel,
//       @Context HttpHeaders httpheaders)
//       throws SQLException, NoSuchAlgorithmException {
//     ResponseModel responseModel =
//         new ResponseModel(String.valueOf(ResponseCodeConstant.SOMETHING_IS_WRONG), null);
//     if (salesmanDAO.isAuthTokenExists(httpheaders.getHeaderString("authToken"))) {
//       responseModel.setResponse(String.valueOf(ResponseCodeConstant.EVERYTHING_IS_OK));
//       responseModel.setData(
//           new Gson()
//               .toJson(
//                   productSalesmanDAO.createSalesmanProduct(productSalesmanDatabaseRequestModel)));
//     } else {
//       responseModel.setResponse(
//           String.valueOf(ResponseCodeConstant.AUTH_TOKEN_NOT_FOUND_OR_NOT_MATCHED));
//     }
//     return responseModel;
//   }
//
//   @Path("removeSalesmanProduct")
//   @POST
//   @Consumes({MediaType.APPLICATION_JSON})
//   @Produces(MediaType.APPLICATION_JSON)
//   public ResponseModel removeSalesmanProduct(
//       ProductSalesmanDatabaseRequestModel productSalesmanDatabaseRequestModel,
//       @Context HttpHeaders httpheaders)
//       throws SQLException {
//     ResponseModel responseModel =
//         new ResponseModel(String.valueOf(ResponseCodeConstant.SOMETHING_IS_WRONG), null);
//     if (salesmanDAO.isAuthTokenExists(httpheaders.getHeaderString("authToken"))) {
//       responseModel.setResponse(String.valueOf(ResponseCodeConstant.EVERYTHING_IS_OK));
//       responseModel.setData(
//           new Gson()
//               .toJson(
//                   productSalesmanDAO.removeSalesmanProduct(productSalesmanDatabaseRequestModel)));
//     } else {
//       responseModel.setResponse(
//           String.valueOf(ResponseCodeConstant.AUTH_TOKEN_NOT_FOUND_OR_NOT_MATCHED));
//     }
//     return responseModel;
//   }
//
//   @Path("getProductIdSalesmanIdMappingList")
//   @POST
//   @Consumes({MediaType.APPLICATION_JSON})
//   @Produces(MediaType.APPLICATION_JSON)
//   public ResponseModel getProductIdSalesmanIdMappingList(
//       ProductSalesmanDatabaseRequestModel productSalesmanDatabaseRequestModel,
//       @Context HttpHeaders httpheaders)
//       throws SQLException {
//     ResponseModel responseModel =
//         new ResponseModel(String.valueOf(ResponseCodeConstant.SOMETHING_IS_WRONG), null);
//     if (salesmanDAO.isAuthTokenExists(httpheaders.getHeaderString("authToken"))) {
//       responseModel.setResponse(String.valueOf(ResponseCodeConstant.EVERYTHING_IS_OK));
//       responseModel.setData(
//           new Gson()
//               .toJson(
//                   productSalesmanDAO.getAllProductSalesmanMapping(
//                       productSalesmanDatabaseRequestModel)));
//     } else {
//       responseModel.setResponse(
//           String.valueOf(ResponseCodeConstant.AUTH_TOKEN_NOT_FOUND_OR_NOT_MATCHED));
//     }
//     return responseModel;
//   }
//
//   @Path("getSalesmanProductDetailList")
//   @POST
//   @Consumes({MediaType.APPLICATION_JSON})
//   @Produces(MediaType.APPLICATION_JSON)
//   public ResponseModel getSalesmanProductDetailList(
//       ProductSalesmanDatabaseRequestModel productSalesmanDatabaseRequestModel,
//       @Context HttpHeaders httpheaders)
//       throws SQLException {
//     ResponseModel responseModel =
//         new ResponseModel(String.valueOf(ResponseCodeConstant.SOMETHING_IS_WRONG), null);
//     if (salesmanDAO.isAuthTokenExists(httpheaders.getHeaderString("authToken"))) {
//       responseModel.setResponse(String.valueOf(ResponseCodeConstant.AUTH_TOKEN_FOUND_OR_MATCHED));
//       responseModel.setData(
//           new Gson()
//               .toJson(
//                   productSalesmanDAO.getAllProductsRegisteredByUser(
//                       productSalesmanDAO.getAllProductSalesmanMapping(
//                           productSalesmanDatabaseRequestModel))));
//       responseModel.setResponse(String.valueOf(ResponseCodeConstant.EVERYTHING_IS_OK));
//     } else {
//       responseModel.setResponse(
//           String.valueOf(ResponseCodeConstant.AUTH_TOKEN_NOT_FOUND_OR_NOT_MATCHED));
//     }
//     return responseModel;
//   }
// }

package api.requests.skeleton.requesters;

import api.models.BaseModel;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.HTTPRequest;
import api.requests.skeleton.interfaces.CrudEndpointInterface;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ValidatedCrudRequester<M extends BaseModel> extends HTTPRequest implements CrudEndpointInterface {
  private final CrudRequester crudRequester;

  public ValidatedCrudRequester(RequestSpecification requestSpecification, Endpoint endpoint, ResponseSpecification responseSpecification) {
    super(requestSpecification, endpoint, responseSpecification);
    crudRequester = new CrudRequester(requestSpecification, endpoint, responseSpecification);
  }

  @Override
  public M post(BaseModel model) {
    return (M) crudRequester.post(model).extract().as(endpoint.getResponseModel());
  }


  @Override
  public Object get() {
    return null;
  }

  @Override
  public Object put(BaseModel model) {
    return null;
  }

  @Override
  public Object delete(int id) {
    return null;
  }
}

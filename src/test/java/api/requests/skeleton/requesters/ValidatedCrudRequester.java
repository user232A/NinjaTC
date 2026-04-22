package api.requests.skeleton.requesters;

import api.models.BaseModel;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.HTTPRequest;
import api.requests.skeleton.interfaces.CrudEndpointInterface;
import api.requests.skeleton.interfaces.GetAllEndpointInterface;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Arrays;
import java.util.List;

public class ValidatedCrudRequester<M extends BaseModel> extends HTTPRequest implements CrudEndpointInterface {
    private final CrudRequester crudRequester;

    public class ValidatedCrudRequester<T extends BaseModel> extends HTTPRequest implements CrudEndpointInterface, GetAllEndpointInterface {

        private CrudRequester crudRequester;

        public ValidatedCrudRequester(RequestSpecification requestSpecification, Endpoint endpoint, ResponseSpecification responseSpecification) {
            super(requestSpecification, endpoint, responseSpecification);
            this.crudRequester = new CrudRequester(requestSpecification, endpoint, responseSpecification);
        }

        @Override
        public M post(BaseModel model) {
            return (M) crudRequester.post(model).extract().as(endpoint.getResponseModel());
        }


        @Override
        public M get() {
            return (M) crudRequester.get().extract().as(endpoint.getResponseModel());
        }

        @Override
        public M put(BaseModel model) {
            return (M) crudRequester.put(model).extract().as(endpoint.getResponseModel());
        }

        @Override
        public Object delete(int id) {
            return null;
        }

        public ValidatedCrudRequester<T> withPathParam(String param, String value) {
            this.pathParams.put(param, value);
            crudRequester.withPathParam(param, value);
            return this;
        }

        public ValidatedCrudRequester<T> withQueryParam(String param, String value) {
            this.queryParams.put(param, value);
            crudRequester.withQueryParam(param, value);
            return this;
        }

        @Override
        public List<M> getAll(Class<?> clazz) {
            M[] array = (M[]) crudRequester.getAll(clazz).extract().as(clazz);
            return Arrays.asList(array);
        }

        public <R> R getAs(Class<R> clazz) {
            return crudRequester.get().extract().as(clazz);
        }
    }
}

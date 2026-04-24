package tests.api;

import api.configs.Config;
import api.models.request.CreateVcsRootRequest;
import api.models.response.CreateVcsRootResponse;
import api.requests.skeleton.Endpoint;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import org.junit.jupiter.api.Test;

import static api.generators.VcsRootRequestGenerator.*;

public class CreateVcsRootTests extends BaseTest {

    @Test
    public void adminCanCreateVcsRoot() {

        CreateVcsRootRequest vcsRootRequest = CreateVcsRootRequest.builder()
                .name(uniqueVcsRootName())
                .vcsName(Config.getProperty("vcs.root.vcsName"))
                .project(defaultRootProject())
                .properties(defaultProperties())
                .build();

        CreateVcsRootResponse vcsRootResponse = new ValidatedCrudRequester<CreateVcsRootResponse>(
                RequestSpecs.adminSpec(),
                Endpoint.VCS_ROOTS,
                ResponseSpecs.operationOk())
                .post(vcsRootRequest);

        softly.assertThat(vcsRootResponse.getId()).isNotBlank();
        softly.assertThat(vcsRootResponse.getName()).isEqualTo(vcsRootRequest.getName());
        softly.assertThat(vcsRootResponse.getVcsName()).isEqualTo(vcsRootRequest.getVcsName());
        softly.assertThat(vcsRootResponse.getProject().getId()).isEqualTo(vcsRootRequest.getProject().getId());
    }
}
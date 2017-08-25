package net.massdriver.orbit.rest.endpoints;

import com.jcabi.urn.URN;
import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.chunk.PathAsset;
import com.nsnc.massdriver.nitrite.NitriteAsset;
import com.nsnc.massdriver.nitrite.NitriteDriver;
import org.dizitart.no2.meta.Attributes;
import org.dizitart.no2.store.NitriteMap;
import org.dizitart.no2.store.NitriteStore;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.util.Set;


@ApplicationScoped
@Path("/urn")
@Produces("application/json")
@Consumes("application/json")
public class URNEndpointImpl implements URNEndpoint {

    @Inject
    private NitriteDriver nitriteDriver;


    @GET
    @Override
    public String makeURN() {
        NitriteAsset nitriteAsset = nitriteDriver.findAssets().findFirst().orElse(null);
        if (nitriteAsset != null) {
            return nitriteAsset.getUrn();
        }
        return null;
    }
}
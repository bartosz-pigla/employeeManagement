package avra.hrsystem.employeemanagement.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"prod"})
public class ProdService {
    private String prodService="prod";

    public String getProdService() {
        return prodService;
    }

    public void setProdService(String prodService) {
        this.prodService = prodService;
    }
}

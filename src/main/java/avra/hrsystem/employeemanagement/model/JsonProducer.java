package avra.hrsystem.employeemanagement.model;

import avra.hrsystem.employeemanagement.model.dto.Pair;
import avra.hrsystem.employeemanagement.service.StringToJsonConverter;

@FunctionalInterface
public interface JsonProducer {
    StringBuilder produce(Pair pair, StringBuilder stringBuilder);
}

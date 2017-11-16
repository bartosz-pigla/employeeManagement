package avra.hrsystem.employeemanagement.service;

import avra.hrsystem.employeemanagement.model.JsonProducer;
import avra.hrsystem.employeemanagement.model.dto.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StringToJsonConverter {
    public String convert(List<Pair> pairList, JsonProducer jsonProducer){
        return convertHelper(pairList,jsonProducer);
    }

    public String convert(List<Pair> pairList){
        return convertHelper(pairList,StringToJsonConverter::convertFieldWithDoubleQuote);
    }

    private String convertHelper(List<Pair> pairList, JsonProducer jsonProducer){
        StringBuilder sb=new StringBuilder();

        sb.append("{");

        pairList.forEach(p->jsonProducer.produce(p,sb).append(", "));

        int lastComma=sb.lastIndexOf(",");
        sb.replace(lastComma,lastComma+2,"");

        sb.append("}");

        return sb.toString();
    }

    private static StringBuilder convertFieldWithDoubleQuote(Pair pair, StringBuilder sb){
        sb
                .append("\"").append(pair.getKey()).append("\"").append(":")
                .append("\"").append(pair.getValue()).append("\"");
        return sb;
    }

    public static StringBuilder convertFieldWithoutDoubleQuote(Pair pair, StringBuilder sb){
        sb
                .append(pair.getKey()).append(":")
                .append(pair.getValue());
        return sb;
    }
}

package cl.sarayar.gestorTareasRest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cl.sarayar.gestorTareasRest.entities.Secuencia;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

@Service
public class GeneradorSecuenciaServiceImpl implements GeneradorSecuenciaService {

	private MongoOperations mongoOperations;
	
	@Autowired
	public GeneradorSecuenciaServiceImpl(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
	
	@Override
	public long generadorSecuencia(String nombre) {
		
		Secuencia secuencia = mongoOperations.findAndModify(query(where("_id").is(nombre)),
			      new Update().inc("seq",1), options().returnNew(true).upsert(true), Secuencia.class);
			    return !Objects.isNull(secuencia) ? secuencia.getSeq() : 1;
	}

	
}

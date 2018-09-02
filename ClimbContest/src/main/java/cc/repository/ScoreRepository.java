package cc.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import cc.model.Climber;
import cc.model.IfscScore;

@Repository
public class ScoreRepository {

	private Firestore db;

	public ScoreRepository(String projectId) throws InterruptedException, ExecutionException {
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId(projectId)
				.build();
		db = firestoreOptions.getService();

		// some sample data
		saveClimber(new Climber("Pio", 32, 73, new IfscScore(4, 19, 6, 16)));
		saveClimber(new Climber("Fell", 100, 100, new IfscScore(0, 100, 1, 50)));
	}

	public void saveClimber(Climber climber) throws InterruptedException, ExecutionException {
		DocumentReference docRef = db.collection("scores-test").document(climber.getName());
		Map<String, Object> data = new HashMap<>();
		data.put("name", climber.getName());
		data.put("ifscScore", climber.getIfscScore().toString());
		data.put("weight", climber.getWeight());
		data.put("age", climber.getAge());
		docRef.set(data);
	}

	public List<Climber> loadClimbers() throws InterruptedException, ExecutionException {
		List<Climber> res = new ArrayList<>();

		// asynchronously retrieve all users
		ApiFuture<QuerySnapshot> query = db.collection("scores-test").get();
		// ...
		// query.get() blocks on response
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			String name = document.getString("name");
			Integer age = document.getLong("age").intValue();
			Integer weight = document.getLong("weight").intValue();
			IfscScore ifscScore = IfscScore.parseString(document.getString("ifscScore"));
			Climber climber = new Climber(name, age, weight, ifscScore);
			res.add(climber);
		}

		return res;
	}
}

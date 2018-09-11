package cc.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.Transaction;

import cc.model.Attempt;
import cc.model.Climber;
import cc.model.IfscScore;
import cc.service.IfscCalculator;

@Repository
public class ClimbersRepository {

	private Firestore db;
	@Autowired
	private IfscCalculator calculator;

	public ClimbersRepository(String projectId) throws InterruptedException, ExecutionException {
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId(projectId)
				.setTimestampsInSnapshotsEnabled(true).build();
		db = firestoreOptions.getService();

		// some sample data
		// saveClimber(new Climber(0, "Pio", "FFK", "pro", new IfscScore(4, 19, 6,
		// 16)));
		// saveClimber(new Climber(1, "Fell", "FFK", "lajt", new IfscScore(0, 100, 1,
		// 50)));
	}

	public void saveClimber(Climber climber) throws InterruptedException, ExecutionException {
		DocumentReference docRef = db.collection("scores-test").document(climber.getName());
		Map<String, Object> data = new HashMap<>();
		data.put("id", climber.getId());
		data.put("name", climber.getName());
		data.put("ifscScore", climber.getIfscScore().toString());
		data.put("club", climber.getClub());
		data.put("category", climber.getCategory());
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
			int id = document.getLong("id").intValue();
			String name = document.getString("name");
			String club = document.getString("club");
			String category = document.getString("category");
			IfscScore ifscScore = IfscScore.parseString(document.getString("ifscScore"));
			Climber climber = new Climber(id, name, club, category, ifscScore);
			res.add(climber);
		}

		return res;
	}

	public void updateClimber(Attempt attempt, Climber climber) {
		DocumentReference docRef = db.collection("scores-test").document(climber.getName());
		db.runTransaction(new Transaction.Function<Void>() {
			@Override
			public Void updateCallback(Transaction transaction) throws Exception {
				// retrieve document and increment population field
				DocumentSnapshot document = transaction.get(docRef).get();
				IfscScore ifscScore = IfscScore.parseString(document.getString("ifscScore"));
				transaction.update(docRef, "ifscScore",
						calculator.addAttempt(ifscScore, attempt.getEffect()).toString());
				return null;
			}
		});

	}
}

package cc.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import cc.model.climber.Climber;

@Repository
public class ClimbersRepository {

	@Autowired
	private Firestore db;

//	public ClimbersRepository(String projectId) {
//		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId(projectId)
//				.setTimestampsInSnapshotsEnabled(true).build();
//		db = firestoreOptions.getService();
//
////		 some sample data
////		 saveClimber(new Climber(0, "Pio", "FFK", "pro"));
////		 saveClimber(new Climber(1, "Fell", "FFK", "lajt"));
//	}

	public void saveClimber(Climber climber) {
		DocumentReference docRef = db.collection("scores-test").document(climber.getName());
		Map<String, Object> data = new HashMap<>();
		data.put("id", climber.getId());
		data.put("name", climber.getName());
		data.put("score", climber.getScore().toString());
		data.put("routes", routeScoresToString(climber.getRouteScores()));
		data.put("club", climber.getClub());
		data.put("category", climber.getCategory());
		docRef.set(data);
	}

	private static String routeScoresToString(List<String> routeScores) {
		String res = "";
		for (String score : routeScores) {
			res += score;
			res += ";";
		}
		if (res.endsWith(";")) {
			res = res.substring(0, res.length() - 1);
		}
		return res;
	}

	private static List<String> stringToRouteScores(String stringWithRouteScores) {
		List<String> res = new ArrayList<>();
		if (StringUtils.isEmpty(stringWithRouteScores)) {
			return res;
		}
		String[] stringScores = stringWithRouteScores.split(";");
		for (String stringScore : stringScores) {
			res.add(stringScore);
		}
		return Collections.unmodifiableList(res);
	}

	public List<Climber> loadClimbers() {
		List<Climber> res = new ArrayList<>();

		// asynchronously retrieve all users
		ApiFuture<QuerySnapshot> query = db.collection("scores-test").get();
		// ...
		// query.get() blocks on response
		QuerySnapshot querySnapshot;
		try {
			querySnapshot = query.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			int id = document.getLong("id").intValue();
			String name = document.getString("name");
			String club = document.getString("club");
			String category = document.getString("category");
			String score = document.getString("score");
			List<String> routeScores = stringToRouteScores(document.getString("routes"));
			Climber climber = new Climber(id, name, club, category, score, routeScores);
			res.add(climber);
		}

		return res;
	}

}

package cc.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import cc.model.Climber;
import cc.model.IfscScore;

@Repository
public class ClimbersRepository {

	private Firestore db;


	public ClimbersRepository(String projectId) throws InterruptedException, ExecutionException {
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId(projectId)
				.setTimestampsInSnapshotsEnabled(true).build();
		db = firestoreOptions.getService();

//		 some sample data
//		 saveClimber(new Climber(0, "Pio", "FFK", "pro"));
//		 saveClimber(new Climber(1, "Fell", "FFK", "lajt"));
	}

	public void saveClimber(Climber climber) throws InterruptedException, ExecutionException {
		DocumentReference docRef = db.collection("scores-test").document(climber.getName());
		Map<String, Object> data = new HashMap<>();
		data.put("id", climber.getId());
		data.put("name", climber.getName());
		data.put("ifscScore", climber.getIfscScore().toString());		
		data.put("routes", routeScoresToString(climber.getRouteScores()));
		data.put("club", climber.getClub());
		data.put("category", climber.getCategory());
		docRef.set(data);
	}
	
	private static String routeScoresToString(List<IfscScore> routeScores) {
		String res = "";
		for (IfscScore score : routeScores) {
			res += score.toString();
			res += ";";
		}
		if (res.endsWith(";")) {
			res = res.substring(0, res.length()-1);
		}
		return res;
	}
	
	private static List<IfscScore> stringToRouteScores(String stringWithRouteScores) {
		List<IfscScore> res = new ArrayList<>();
		if (StringUtils.isEmpty(stringWithRouteScores)) {
			return res;
		}
		String[] stringScores = stringWithRouteScores.split(";");
		for (String stringScore : stringScores) {
			res.add(IfscScore.parseString(stringScore));
		}
		return Collections.unmodifiableList(res);
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
			List<IfscScore> routeScores = stringToRouteScores(document.getString("routes"));
			Climber climber = new Climber(id, name, club, category, ifscScore, routeScores);
			res.add(climber);
		}

		return res;
	}

}

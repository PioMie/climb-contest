package cc.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

@Repository
public class ParamsRepository {
	
	@Autowired
	private Firestore db;

	public void saveParams(Map<String, String> params) {
		DocumentReference docRef = db.collection("slb-19-20-params").document("params");
		Map<String, Object> data = new HashMap<>();
		data.putAll(loadParams());
		data.putAll(params);
		docRef.set(data);
	}
	
	public Map<String, Object> loadParams() {
		DocumentReference docRef = db.collection("slb-19-20-params").document("params");
		DocumentSnapshot document;
		try {
			ApiFuture<DocumentSnapshot> docSnapshot;
			docSnapshot = docRef.get();
			document = docSnapshot.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}

		return document.getData();
	}
	
}

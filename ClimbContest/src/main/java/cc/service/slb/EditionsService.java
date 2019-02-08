package cc.service.slb;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.repository.ParamsRepository;

@Service
public class EditionsService {

	private static final String CURRENT_EDITION = "CURRENT_EDITION";
	private static final String COMPLETED_EDITIONS = "COMPLETED_EDITIONS";

	@Autowired
	ParamsRepository paramsRepository;

	public void saveCurrentEdition(int currentEdition) {
		Map<String, String> params = new HashMap<>();
		params.put(CURRENT_EDITION, Integer.toString(currentEdition));
		paramsRepository.saveParams(params);
	}

	public void saveCompletedEditions(int completedEditions) {
		Map<String, String> params = new HashMap<>();
		params.put(COMPLETED_EDITIONS, Integer.toString(completedEditions));
		paramsRepository.saveParams(params);
	}

	public int loadCurrentEditon() {
		Map<String, Object> params = paramsRepository.loadParams();
		Object ob = params.get(CURRENT_EDITION);
		return Integer.parseInt(ob.toString());
	}

	public int loadCompletedEditions() {
		Map<String, Object> params = paramsRepository.loadParams();
		Object ob = params.get(COMPLETED_EDITIONS);
		return Integer.parseInt(ob.toString());
	}
}

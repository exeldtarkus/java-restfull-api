package co.id.adira.moservice.contentservice.json.adiraku.activity;

import lombok.Data;

@Data
public class AdirakuMsActivityCreateActivityJson {

	private String group;
	private String subGroup;
	private String title;
	private String content;
	private String linkTo;
	private AdirakuMsActivityCreateActivityPassParamJson passParam;
	private String accountId;

}

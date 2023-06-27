
function goToVote(callbackFunction) {
	$.get(
		"HomeController",
		{ action: "goToVote" },
		callbackFunction
	);
}

function uploadNew(userID, url) {
    $.getJSON("HomeController",
		{ action: "uploadNew",
			userID: userID,
			url: url
		}
	);
}

function seeTop(topNr, callbackFunction) {
	$.get("HomeController",
		{ action: "seeTop",
			topNr: topNr
		},
		callbackFunction
	)
}

function logout() {
	$.get("HomeController",
		{ action:"logout" }
	)
}
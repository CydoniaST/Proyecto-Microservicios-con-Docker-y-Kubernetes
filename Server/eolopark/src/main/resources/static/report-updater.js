window.addEventListener('load', (event) => {

    console.log("WebSocket")

    let socket = new WebSocket("wss://"+window.location.host+"/notifications?parkId="+parkId);

    socket.onmessage = function (event) {

        console.log(`[message] Data received from server: ${event.data}`);

        let report = JSON.parse(event.data);

        let progressElem = document.getElementById('progress');
        let reportDataElem = document.getElementById('report-data');

        progressElem.textContent = report.progress + '%';
        reportDataElem.textContent = report.reportData;

    };

});

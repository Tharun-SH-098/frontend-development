/* ==========================================
   PHONE CALL MANAGEMENT SYSTEM
   PROFESSIONAL JAVASCRIPT
========================================== */

// ==============================
// GLOBAL VARIABLES
// ==============================

let calls =
JSON.parse(localStorage.getItem("calls")) || [];

let chart = null;

// ==============================
// INITIALIZATION
// ==============================

document.addEventListener("DOMContentLoaded", () => {

    initializeTheme();

    renderTable();

    updateStatistics();

    initializeChart();

    registerEvents();

});

// ==============================
// EVENT REGISTRATION
// ==============================

function registerEvents() {

    const form =
    document.getElementById("callForm");

    if(form){
        form.addEventListener(
            "submit",
            addCall
        );
    }

    const themeBtn =
    document.getElementById("themeBtn");

    if(themeBtn){
        themeBtn.addEventListener(
            "click",
            toggleTheme
        );
    }

    const search =
    document.getElementById("globalSearch");

    if(search){
        search.addEventListener(
            "keyup",
            searchCalls
        );
    }

    const exportBtn =
    document.getElementById("exportBtn");

    if(exportBtn){
        exportBtn.addEventListener(
            "click",
            exportCSV
        );
    }

}

// ==============================
// ADD CALL
// ==============================

function addCall(event){

    event.preventDefault();

    const name =
    document.getElementById("callerName").value;

    const number =
    document.getElementById("callerNumber").value;

    const status =
    document.getElementById("callStatus").value;

    const department =
    document.getElementById("department").value;

    const priority =
    document.getElementById("priority").value;

    if(
        name.trim() === "" ||
        number.trim() === ""
    ){
        alert(
            "Please fill all fields."
        );
        return;
    }

    const call = {

        id:
        Date.now(),

        name,

        number,

        status,

        department,

        priority,

        date:
        new Date()
        .toLocaleString()

    };

    calls.push(call);

    saveCalls();

    renderTable();

    updateStatistics();

    updateChart();

    showNotification(
        "Call added successfully"
    );

    document
    .getElementById("callForm")
    .reset();

}

// ==============================
// SAVE DATA
// ==============================

function saveCalls(){

    localStorage.setItem(
        "calls",
        JSON.stringify(calls)
    );

}

// ==============================
// TABLE RENDERING
// ==============================

function renderTable(){

    const tbody =
    document.getElementById(
        "callTableBody"
    );

    if(!tbody) return;

    tbody.innerHTML = "";

    calls.forEach(call => {

        let statusClass = "";

        if(
            call.status ===
            "Completed"
        ){
            statusClass =
            "status-completed";
        }

        if(
            call.status ===
            "Missed"
        ){
            statusClass =
            "status-missed";
        }

        if(
            call.status ===
            "Ongoing"
        ){
            statusClass =
            "status-ongoing";
        }

        tbody.innerHTML += `
        <tr>

            <td>${call.id}</td>

            <td>${call.name}</td>

            <td>${call.number}</td>

            <td class="${statusClass}">
                ${call.status}
            </td>

            <td>${call.department}</td>

            <td>${call.priority}</td>

            <td>${call.date}</td>

            <td>

                <button
                class="btn-edit"
                onclick="editCall(${call.id})">

                Edit

                </button>

                <button
                class="btn-delete"
                onclick="deleteCall(${call.id})">

                Delete

                </button>

            </td>

        </tr>
        `;

    });

}

// ==============================
// DELETE CALL
// ==============================

function deleteCall(id){

    const result =
    confirm(
        "Delete this record?"
    );

    if(!result){
        return;
    }

    calls =
    calls.filter(
        call =>
        call.id !== id
    );

    saveCalls();

    renderTable();

    updateStatistics();

    updateChart();

    showNotification(
        "Call deleted"
    );

}

// ==============================
// EDIT CALL
// ==============================

function editCall(id){

    const call =
    calls.find(
        c => c.id === id
    );

    if(!call){
        return;
    }

    const newName =
    prompt(
        "Edit Name",
        call.name
    );

    if(newName === null){
        return;
    }

    const newNumber =
    prompt(
        "Edit Number",
        call.number
    );

    if(newNumber === null){
        return;
    }

    call.name =
    newName;

    call.number =
    newNumber;

    saveCalls();

    renderTable();

    showNotification(
        "Call updated"
    );

}

// ==============================
// SEARCH
// ==============================

function searchCalls(){

    const value =
    document
    .getElementById(
        "globalSearch"
    )
    .value
    .toLowerCase();

    const rows =
    document.querySelectorAll(
        "#callTableBody tr"
    );

    rows.forEach(row => {

        const text =
        row.innerText
        .toLowerCase();

        row.style.display =
        text.includes(value)
        ? ""
        : "none";

    });

}

// ==============================
// DASHBOARD STATS
// ==============================

function updateStatistics(){

    let completed = 0;
    let missed = 0;
    let ongoing = 0;

    calls.forEach(call => {

        if(
            call.status ===
            "Completed"
        ){
            completed++;
        }

        if(
            call.status ===
            "Missed"
        ){
            missed++;
        }

        if(
            call.status ===
            "Ongoing"
        ){
            ongoing++;
        }

    });

    const totalCalls =
    document.getElementById(
        "totalCalls"
    );

    const completedCalls =
    document.getElementById(
        "completedCalls"
    );

    const missedCalls =
    document.getElementById(
        "missedCalls"
    );

    const ongoingCalls =
    document.getElementById(
        "ongoingCalls"
    );

    if(totalCalls){
        totalCalls.innerText =
        calls.length;
    }

    if(completedCalls){
        completedCalls.innerText =
        completed;
    }

    if(missedCalls){
        missedCalls.innerText =
        missed;
    }

    if(ongoingCalls){
        ongoingCalls.innerText =
        ongoing;
    }

}

// ==============================
// CHART
// ==============================

function initializeChart(){

    const canvas =
    document.getElementById(
        "callChart"
    );

    if(!canvas){
        return;
    }

    chart =
    new Chart(canvas,{

        type:"doughnut",

        data:{
            labels:[
                "Completed",
                "Missed",
                "Ongoing"
            ],
            datasets:[
                {
                    data:[0,0,0],
                    backgroundColor:[
                        "#16a34a",
                        "#dc2626",
                        "#f59e0b"
                    ]
                }
            ]
        },

        options:{
            responsive:true,
            maintainAspectRatio:false
        }

    });

    updateChart();

}

function updateChart(){

    if(!chart){
        return;
    }

    let completed = 0;
    let missed = 0;
    let ongoing = 0;

    calls.forEach(call => {

        if(
            call.status ===
            "Completed"
        ){
            completed++;
        }

        if(
            call.status ===
            "Missed"
        ){
            missed++;
        }

        if(
            call.status ===
            "Ongoing"
        ){
            ongoing++;
        }

    });

    chart.data.datasets[0].data = [

        completed,

        missed,

        ongoing

    ];

    chart.update();

}

// ==============================
// DARK MODE
// ==============================

function toggleTheme(){

    document.body
    .classList
    .toggle("dark-mode");

    const isDark =
    document.body
    .classList
    .contains(
        "dark-mode"
    );

    localStorage.setItem(
        "theme",
        isDark
    );

}

function initializeTheme(){

    const savedTheme =
    localStorage.getItem(
        "theme"
    );

    if(savedTheme === "true"){

        document.body
        .classList
        .add(
            "dark-mode"
        );

    }

}

// ==============================
// EXPORT CSV
// ==============================

function exportCSV(){

    let csv =
    "ID,Name,Phone,Status,Department,Priority,Date\n";

    calls.forEach(call => {

        csv +=
        `${call.id},${call.name},${call.number},${call.status},${call.department},${call.priority},${call.date}\n`;

    });

    const blob =
    new Blob(
        [csv],
        {
            type:
            "text/csv"
        }
    );

    const link =
    document.createElement(
        "a"
    );

    link.href =
    URL.createObjectURL(
        blob
    );

    link.download =
    "Call_Reports.csv";

    link.click();

    showNotification(
        "CSV exported"
    );

}

// ==============================
// NOTIFICATIONS
// ==============================

function showNotification(
    message
){

    const note =
    document.createElement(
        "div"
    );

    note.innerText =
    message;

    note.style.position =
    "fixed";

    note.style.right =
    "20px";

    note.style.top =
    "20px";

    note.style.background =
    "#16a34a";

    note.style.color =
    "white";

    note.style.padding =
    "15px 20px";

    note.style.borderRadius =
    "10px";

    note.style.zIndex =
    "9999";

    note.style.boxShadow =
    "0 5px 15px rgba(0,0,0,.3)";

    document.body
    .appendChild(note);

    setTimeout(() => {

        note.remove();

    },3000);

}

// ==============================
// DEMO DATA
// ==============================

if(calls.length === 0){

    calls.push({

        id:1,

        name:
        "Rahul Sharma",

        number:
        "9876543210",

        status:
        "Completed",

        department:
        "Sales",

        priority:
        "Medium",

        date:
        new Date()
        .toLocaleString()

    });

    calls.push({

        id:2,

        name:
        "Ananya Rao",

        number:
        "9988776655",

        status:
        "Missed",

        department:
        "Support",

        priority:
        "High",

        date:
        new Date()
        .toLocaleString()

    });

    calls.push({

        id:3,

        name:
        "Vikram Patel",

        number:
        "9123456789",

        status:
        "Ongoing",

        department:
        "Technical",

        priority:
        "Low",

        date:
        new Date()
        .toLocaleString()

    });

    saveCalls();

}
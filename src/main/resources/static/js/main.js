let isSamePayload = true;
let formattedResponses = { A: null, B: null };
let tableDataResponses = { A: null, B: null };
let differences = {};
let isShowOriginResult = false;
let isShowDbKeyConfig = false;

const urlAConfigureInput = document.getElementById('configuredURLA');
const urlBConfigureInput = document.getElementById('configuredURLB');
const jdbcAConfigureInput = document.getElementById('configuredJDBCA');
const jdbcBConfigureInput = document.getElementById('configuredJDBCB');

const urlASaveBtn = document.getElementById('saveUrlAConfigurationBtn');
const urlBSaveBtn = document.getElementById('saveUrlBConfigurationBtn');
const jdbcASaveBtn = document.getElementById('saveJdbcAConfigurationBtn');
const jdbcBSaveBtn = document.getElementById('saveJdbcBConfigurationBtn');

const urlModalCloseSpan = document.getElementById('urlSaveDivClose');
const jdbcModalCloseSpan = document.getElementById('jdbcSaveDivClose');

const urlSaveModal = document.getElementById('urlSaveModal');
const jdbcSaveModal = document.getElementById('jdbcSaveModal');

const urlSaveModalUrlInput = document.getElementById('saveUrl');
const urlSaveModalHeaderInput = document.getElementById('saveHeader');

const jdbcSaveModalJdbcUrlInput = document.getElementById('saveJdbcUrl');
const jdbcSaveModalJdbcUserInput = document.getElementById('saveDbUser');
const jdbcSaveModalJdbcPasswordInput = document.getElementById('saveDbPassword');
const jdbcSaveModalJdbcTypeInput = document.getElementById('saveDbType');


//周一回来搞
// urlAConfigureInput.addEventListener('focusout', () => {
//     if (urlAConfigureInput.value.trim() != '') {
//         if(urlAConfigureInput.value in urlAliasesList) {

//     }
// });

// 展示原始结果
function showOriginResult() {
    isShowOriginResult = !isShowOriginResult;
    document.getElementById('originalResponseShow').classList.toggle('hidden');
    if (isShowOriginResult) {
        document.getElementById('showOriginResultBtn').innerHTML = '隐藏原始结果';
    } else {
        document.getElementById('showOriginResultBtn').innerHTML = '展示原始结果';
    }
}

fetch('/api/url-aliases-get')
    .then(r => r.json())
    .then(data => {
        let datalistA = document.getElementById('configuredURLListA');
        let datalistB = document.getElementById('configuredURLListB');
        data.urlAliasesList.forEach(urlAliases => {
            let optionA = document.createElement('option');
            let optionB = document.createElement('option');
            optionA.value = urlAliases;
            optionB.value = urlAliases;
            datalistA.appendChild(optionA);
            datalistB.appendChild(optionB);
        });
    });

fetch('/api/jdbc-aliases-get')
    .then(r => r.json())
    .then(data => {
        let datalistA = document.getElementById('configuredJDBCListA');
        let datalistB = document.getElementById('configuredJDBCListB');
        data.jdbcAliasesList.forEach(jdbcAliases => {
            let optionA = document.createElement('option');
            let optionB = document.createElement('option');
            optionA.value = jdbcAliases;
            optionB.value = jdbcAliases;
            datalistA.appendChild(optionA);
            datalistB.appendChild(optionB);
        });
    });

// 模式切换
document.getElementById('modeToggle').addEventListener('click', () => {
    isSamePayload = !isSamePayload;
    document.getElementById('modeToggle').textContent =
        isSamePayload ? '切换为不同报文模式' : '切换为相同报文模式';

    document.getElementById('commonPayloadSection').classList.toggle('hidden');
    document.getElementById('payloadSectionA').classList.toggle('hidden');
    document.getElementById('payloadSectionB').classList.toggle('hidden');

    if (isSamePayload) {
        document.getElementById('payloadA').value = document.getElementById('commonPayload').value;
        document.getElementById('payloadB').value = document.getElementById('commonPayload').value;
    }
});

document.getElementById('configuredURLA').addEventListener('blur', fillURLAConfiguration);
document.getElementById('configuredURLB').addEventListener('blur', fillURLBConfiguration);
document.getElementById('configuredJDBCA').addEventListener('blur', fillJDBCAConfiguration);
document.getElementById('configuredJDBCB').addEventListener('blur', fillJDBCBConfiguration);

urlModalCloseSpan.onclick = function () {
    urlSaveModal.style.display = "none";
}
jdbcModalCloseSpan.onclick = function () {
    jdbcSaveModal.style.display = "none";
}

urlASaveBtn.onclick = function () {
    urlSaveModal.style.display = "block";

    urlSaveModalUrlInput.value = document.getElementById('urlA').value;
    urlSaveModalHeaderInput.value = document.getElementById('headerA').value;
}
urlBSaveBtn.onclick = function () {
    urlSaveModal.style.display = "block";
    urlSaveModalUrlInput.value = document.getElementById('urlB').value;
    urlSaveModalHeaderInput.value = document.getElementById('headerB').value;
}
jdbcASaveBtn.onclick = function () {
    jdbcSaveModal.style.display = "block";
    jdbcSaveModalJdbcUrlInput.value = document.getElementById('jdbcUrlA').value;
    jdbcSaveModalJdbcUserInput.value = document.getElementById('dbUserA').value;
    jdbcSaveModalJdbcPasswordInput.value = document.getElementById('dbPasswordA').value;
    jdbcSaveModalJdbcTypeInput.value = document.querySelector('select[name="dbTpyeSelectA"]').value;
}
jdbcBSaveBtn.onclick = function () {
    jdbcSaveModal.style.display = "block";
    jdbcSaveModalJdbcUrlInput.value = document.getElementById('jdbcUrlB').value;
    jdbcSaveModalJdbcUserInput.value = document.getElementById('dbUserB').value;
    jdbcSaveModalJdbcPasswordInput.value = document.getElementById('dbPasswordB').value;
    jdbcSaveModalJdbcTypeInput.value = document.querySelector('select[name="dbTpyeSelectB"]').value;
}

async function sendRequests() {
    const payload = {
        urlA: document.getElementById('urlA').value,
        urlB: document.getElementById('urlB').value,
        headerA: document.getElementById('headerA').value,
        headerB: document.getElementById('headerB').value,
        payloadA: isSamePayload ?
            document.getElementById('commonPayload').value :
            document.getElementById('payloadA').value,
        payloadB: isSamePayload ?
            document.getElementById('commonPayload').value :
            document.getElementById('payloadB').value
    }

    try {
        payloadAJson = JSON.parse(payload.payloadA);
        payloadBJson = JSON.parse(payload.payloadB);

        payload.payloadA = JSON.stringify(payloadAJson);
        payload.payloadB = JSON.stringify(payloadBJson);
    } catch (error) {
        window.alert('输入报文含有非法json字符串', error);
    }

    try {
        const response = await fetch('/api/send-request', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json;charset=UTF-8' },
            body: JSON.stringify(payload)
        });

        const result = await response.json();
        formattedResponses = { A: result.resultA, B: result.resultB };
        differences = JsDiff.createTwoFilesPatch('resutl-A', 'result-B', reponseSortedFormat(formattedResponses.A), reponseSortedFormat(formattedResponses.B));
        displayResponses(differences);

        let urlAbtn = document.getElementById('saveUrlAConfigurationBtn');
        urlAbtn.classList.remove('hidden');
        let urlBbtn = document.getElementById('saveUrlBConfigurationBtn');
        urlAbtn.classList.remove('hidden');

    } catch (error) {
        console.error('请求失败:', error);
    }
}

function displayResponses(diff) {
    const responseA = document.getElementById('responseA');
    const responseB = document.getElementById('responseB');
    const diffResultContent = document.getElementById('diffResultContent');

    responseA.innerHTML = reponseFormat(formattedResponses.A);
    responseB.innerHTML = reponseFormat(formattedResponses.B);

    diffResultContent.innerHTML = Diff2Html.html(diff, {
        inputFormat: "diff",
        showFiles: false,
        drawFileList: false,
        diffStyle: "char",
        matching: "lines",
        highlight: true,
        outputFormat: "side-by-side",
        stickyFileHeaders: false
    });
}

function headAFillDefaultValue(event) {
    if (event.key === 'Enter') {
        document.getElementById("headerA").value = '{"Content-Type": "application/json"}';
        event.preventDefault(); // 防止表单提交
    }
}

function headBFillDefaultValue(event) {
    if (event.key === 'Enter') {
        document.getElementById("headerB").value = '{"Content-Type": "application/json"}';
        event.preventDefault(); // 防止表单提交
    }
}

function urlAFillDefaultValue(event) {
    if (event.key === 'Enter') {
        document.getElementById("urlA").value = 'http://127.0.0.1:8080/api/requestA';
        event.preventDefault(); // 防止表单提交
    }
}

function urlBFillDefaultValue(event) {
    if (event.key === 'Enter') {
        document.getElementById("urlB").value = 'http://127.0.0.1:8080/api/requestB';
        event.preventDefault(); // 防止表单提交
    }
}

function reponseSortedFormat(json) {
    let jsonObject = JSON.parse(json);
    let sortedKeys = Object.keys(jsonObject).sort();
    let sortedObj = {};
    sortedKeys.forEach(key => {
        sortedObj[key] = jsonObject[key];
    });
    let formattedJson = JSON.stringify(jsonObject, null, 4);
    console.log(formattedJson);
    return formattedJson;
}

function reponseFormat(json) {
    let jsonObject = JSON.parse(json);
    let sortedKeys = Object.keys(jsonObject).sort();
    let sortedObj = {};
    sortedKeys.forEach(key => {
        sortedObj[key] = jsonObject[key];
    });
    let formattedJson = JSON.stringify(jsonObject, null, 4);
    console.log(formattedJson);
    return formattedJson;
}


function syntaxHighlight(json) {
    const jsonString = JSON.stringify(json, null, 2);
    return jsonString.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g,
        match => {
            let cls = 'json-number';
            if (/^"/.test(match)) {
                cls = /:$/.test(match) ? 'json-key' : 'json-string';
            } else if (/true|false/.test(match)) {
                cls = 'json-boolean';
            } else if (/null/.test(match)) {
                cls = 'json-null';
            }
            return `<span class="${cls}">${match}</span>`;
        });
}

function syntaxHighlightWithDiff(json, diffs) {
    const jsonString = JSON.stringify(json, null, 2);
    const lines = jsonString.split('\n');
    const diff = Diff.createTwoFilesPatch('A', 'B', jsonString, showResonse(diffs), null, null, { context: 0 });

    return lines.map((line, index) => {
        const diffLine = diff.split('\n')[index + 3]; // Skip the first three lines of the diff output
        if (diffLine.startsWith('+')) {
            return `<div class="diff-added">${syntaxHighlight(line)}</div>`;
        } else if (diffLine.startsWith('-')) {
            return `<div class="diff-removed">${syntaxHighlight(line)}</div>`;
        } else if (diffLine.startsWith('!')) {
            return `<div class="diff-modified">${syntaxHighlight(line)}</div>`;
        }
        return `<div>${syntaxHighlight(line)}</div>`;
    }).join('\n');
}

function sortKeys(obj) {
    if (typeof obj !== 'object' || obj === null) return obj;
    return Object.keys(obj).sort().reduce((acc, key) => {
        acc[key] = sortKeys(obj[key]);
        return acc;
    }, {});
}

async function connectDatabase(env) {
    const config = {
        env: env,
        url: document.getElementById(`jdbcUrl${env}`).value,
        username: document.getElementById(`dbUser${env}`).value,
        password: document.getElementById(`dbPassword${env}`).value,
        dbType: document.querySelector(`select[name="dbTpyeSelect${env}"]`).value,
    };
    const dbKeyConfigDiv = document.getElementById(`dbKeyConfig${env}`);
    const response = await fetch('/api/connect-db', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        body: JSON.stringify(config)
    });

    const result = await response.json();

    if (result.status === 'success') {
        let dbBEelement = document.getElementById(`dbBtableSection${env}`);
        dbBEelement.classList.remove('hidden');
        let tableNameList = document.getElementById(`tableNameList${env}`);
        tableNameList.innerHTML = '';
        result.tablesName.forEach(table => {
            let option = document.createElement('option');
            option.value = table;
            tableNameList.appendChild(option);
        });
    } else {
        alert(`连接失败: ${result.message}`);
        dbKeyConfigDiv.classList.add('hidden');
        return;
    }
}

// 获取表内列名
async function getTableKeys(env) {
    let tableName = document.getElementById(`tablesName${env}`).value;
    let tableQueryConfig = {
        env: env,
        url: document.getElementById(`jdbcUrl${env}`).value,
        username: document.getElementById(`dbUser${env}`).value,
        password: document.getElementById(`dbPassword${env}`).value,
        dbType: document.querySelector(`select[name="dbTpyeSelect${env}"]`).value,
        tableName: tableName
    };
    const tableColumnResponse = await fetch('/api/table-column-get', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        body: JSON.stringify(tableQueryConfig)
    });

    const tableColumnResult = await tableColumnResponse.json();
    if (tableColumnResult.status === 'success') {
        let tableKeyList = document.getElementById(`tableKeyList${env}`);
        tableKeyList.innerHTML = '';
        tableColumnResult.tableColumns.forEach(key => {
            let option = document.createElement('option');
            option.value = key;
            tableKeyList.appendChild(option);
        });
    } else {
        alert(`获取表内列名失败: ${tableColumnResult.message}，环境：${env}`);
    }

}

function showPostTableKeyConfigDiv() {
    document.getElementById('dbKeyConfigA').classList.remove('hidden');
    document.getElementById('dbKeyConfigB').classList.remove('hidden');
    document.getElementById('showPostTableKeyConfigBtn').innerHTML = '重新获取列名数据';

    postKeyConfigByEnv('A');
    postKeyConfigByEnv('B');
    getTableKeys('A');
    getTableKeys('B');
}

function postKeyConfigByEnv(env) {
    let tableKeyList = document.getElementById(`tableKeyList${env}`);
    tableKeyList.innerHTML = '';
    let postKeyList = document.getElementById(`postKeyList${env}`);
    postKeyList.innerHTML = '';

    let keysListPost = getAllKeys(JSON.parse(formattedResponses[`${env}`]));

    keysListPost.forEach(key => {
        let option = document.createElement('option');
        option.value = key;
        postKeyList.appendChild(option);
    });

    keysListPost = []; // 清空数组
}

function getAllKeys(obj, parentKey = '') {
    let keys = [];

    for (const key in obj) {
        // 构造新键
        const newKey = parentKey ? `${parentKey}.${key}` : key;

        if (typeof obj[key] === 'object' && obj[key] !== null) {
            // 如果当前值是对象，递归处理
            keys = keys.concat(getAllKeys(obj[key], newKey));
        } else {
            // 将当前键添加到结果中
            keys.push(newKey);
        }
    }
    return keys;
}

// 遍历对象并获取值
function getValueByKey(obj, keyPath) {
    // 将键路径拆分为数组
    const keys = keyPath.split('.');

    // 遍历路径获取最终值
    let current = obj;

    for (let key of keys) {
        if (current.hasOwnProperty(key)) {
            current = current[key];
        } else {
            return undefined; // 如果路径不存在返回 undefined
        }
    }

    return current; // 返回最终的值
}

function jdbcURLFillDefaultValue(event) {
    if (event.key === 'Enter') {
        document.getElementById("jdbcUrlA").value = 'jdbc:mysql://localhost:3306/test';
        document.getElementById("jdbcUrlB").value = 'jdbc:mysql://localhost:3306/test';
        event.preventDefault(); // 防止表单提交
    }
}

function userFillDefaultValue(event) {
    if (event.key === 'Enter') {
        document.getElementById("dbUserA").value = 'root';
        document.getElementById("dbUserB").value = 'root';
        event.preventDefault(); // 防止表单提交
    }
}

function passwordFillDefaultValue(event) {
    if (event.key === 'Enter') {
        document.getElementById("dbPasswordA").value = 'y32163214';
        document.getElementById("dbPasswordB").value = 'y32163214';
        event.preventDefault(); // 防止表单提交
    }
}

function commonPayloadFillDefaultValue(event) {
    if (event.key === 'Enter') {
        document.getElementById("commonPayload").value = '{"commonKey": "commonValue"}';
        event.preventDefault(); // 防止表单提交
    }
}

function showTableDiff() {
    let tableDiffElement = document.getElementById('diffTableResultContent');
    tableDiffElement.classList.remove('hidden');
    let tableOriginCommonElement = document.getElementById('originalTableShow');
    tableOriginCommonElement.classList.remove('hidden');

    Promise.all([getSingleTableData('A'), getSingleTableData('B')]).then(() => {
        tableDifferences = JsDiff.createTwoFilesPatch('table-A', 'table-B', reponseSortedFormat(tableDataResponses.A), reponseSortedFormat(tableDataResponses.B));
        displayTableDataResult(tableDifferences);
    });
}

async function getSingleTableData(env) {
    let tableName = document.getElementById(`tablesName${env}`).value;
    let columnKey = document.getElementById(`tableKey${env}`).value;
    let columnTempValue = document.getElementById(`postKey${env}`).value;
    let columnValue = getValueByKey(JSON.parse(formattedResponses[`${env}`]), columnTempValue);

    console.log(tableName, columnKey, columnTempValue, columnValue);

    let tableDataQueryConfig = {
        env: env,
        url: document.getElementById(`jdbcUrl${env}`).value,
        username: document.getElementById(`dbUser${env}`).value,
        password: document.getElementById(`dbPassword${env}`).value,
        dbType: document.querySelector(`select[name="dbTpyeSelect${env}"]`).value,
        tableName: tableName,
        columnKey: columnKey,
        columnValue: columnValue
    };

    try {
        const response = await fetch('/api/table-data-json-get', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json;charset=UTF-8' },
            body: JSON.stringify(tableDataQueryConfig)
        });

        const tableResult = await response.json();
        tableDataResponses[`${env}`] = tableResult.tableDataJson;
        console.log(tableDataResponses);

    } catch (error) {
        console.error('请求失败:', error);
    }
}

function displayTableDataResult(diff) {
    const responseA = document.getElementById('tableDataA');
    const responseB = document.getElementById('tableDataB');
    const diffResultContent = document.getElementById('diffTableResultContent');

    responseA.innerHTML = reponseFormat(tableDataResponses.A);
    responseB.innerHTML = reponseFormat(tableDataResponses.B);

    diffResultContent.innerHTML = Diff2Html.html(diff, {
        inputFormat: "diff",
        showFiles: false,
        drawFileList: false,
        diffStyle: "char",
        matching: "lines",
        highlight: true,
        outputFormat: "side-by-side",
        stickyFileHeaders: false
    });
}

async function getUrlConfigurationByUrlAliases(env, urlAliases) {
    if(urlAliases.trim() === "") {
        return;
    }

    let urlConfigurationQueryConfig = {
        urlAliases: urlAliases
    };

    try {
        const response = await fetch('/api/url-get-configuration', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json;charset=UTF-8' },
            body: JSON.stringify(urlConfigurationQueryConfig)
        });

        const tableResult = await response.json();
        fillURLConfigurationByAliases(env, tableResult.toolCompareUrlRegEntity)

    } catch (error) {
        console.error('请求失败:', error);
    }
}

function fillURLConfigurationByAliases(env, toolCompareUrlRegEntity) {
    let url = document.getElementById(`url${env}`);
    let header = document.getElementById(`header${env}`);

    url.value = toolCompareUrlRegEntity.url;
    header.value = toolCompareUrlRegEntity.header;
}

function fillURLAConfiguration() {
    let urlAliases = document.getElementById("configuredURLA").value;
    let toolCompareUrlRegEntity = getUrlConfigurationByUrlAliases('A', urlAliases);

    if(urlAliases.trim() === "") {
        return;
    }

    fillURLConfigurationByAliases('A', toolCompareUrlRegEntity);
}

function fillURLBConfiguration() {
    let urlAliases = document.getElementById("configuredURLB").value;
    let toolCompareUrlRegEntity = getUrlConfigurationByUrlAliases('B', urlAliases);

    if(urlAliases.trim() === "") {
        return;
    }

    fillURLConfigurationByAliases('B', toolCompareUrlRegEntity);
}

async function getJdbcConfigurationByJdbcAliases(env, jdbcAliases) {

    if(jdbcAliases.trim() === "") {
        return;
    }

    let jdbcConfigurationQueryConfig = {
        jdbcAliases: jdbcAliases
    };

    try {
        const response = await fetch('/api/jdbc-get-configuration', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json;charset=UTF-8' },
            body: JSON.stringify(jdbcConfigurationQueryConfig)
        });

        const tableResult = await response.json();
        fillJDBCConfigurationByAliases(env, tableResult.toolCompareJdbcRegEntity)

    } catch (error) {
        console.error('请求失败:', error);
    }
}

function fillJDBCConfigurationByAliases(env, toolCompareJdbcRegEntity) {
    let jdbcUrl = document.getElementById(`jdbcUrl${env}`);
    let jdbcUser = document.getElementById(`dbUser${env}`);
    let jdbcPassword = document.getElementById(`dbPassword${env}`);
    let jdbcType = document.getElementById(`dbTypeSelect${env}`);


    jdbcUrl.value = toolCompareJdbcRegEntity.url;
    jdbcUser.value = toolCompareJdbcRegEntity.jdbcUser;
    jdbcPassword.value = toolCompareJdbcRegEntity.jdbcPassword;
    jdbcType.value = toolCompareJdbcRegEntity.jdbcType;
}

function fillJDBCAConfiguration() {
    let jdbcAliases = document.getElementById("configuredJDBCA").value;
    let toolCompareJdbcRegEntity = getJdbcConfigurationByJdbcAliases('A', jdbcAliases);

    if(jdbcAliases.trim() === "") {
        return;
    }

    fillJDBCConfigurationByAliases('A', toolCompareJdbcRegEntity);
}

function fillJDBCBConfiguration() {
    let jdbcAliases = document.getElementById("configuredJDBCB").value;
    let toolCompareJdbcRegEntity = getJdbcConfigurationByJdbcAliases('B', jdbcAliases);

    if(jdbcAliases.trim() === "") {
        return;
    }

    fillJDBCConfigurationByAliases('B', toolCompareJdbcRegEntity);
}

async function saveUrlConfig() {
    let urlSaveConfig = {
        url : urlSaveModalUrlInput.value.trim(),
        header : urlSaveModalHeaderInput.value.trim(),
        aliasesUrl: document.getElementById(`saveAliasesUrl`).value.trim(),
        idUrlHeader: (urlSaveModalUrlInput.value + "|" + urlSaveModalHeaderInput.value).trim()
    };

    if(urlSaveModalUrlInput.value.trim() === "" || urlSaveModalHeaderInput.valuetrim() === "" || document.getElementById(`saveAliasesUrl`).value,trim() === "") {
        alert("请填写完整URL配置信息,别名不允许为空!")
        return;
    }

    const urlSaveResponse = await fetch('/api/url-save-configuration', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        body: JSON.stringify(urlSaveConfig)
    });

    const result = await urlSaveResponse.json();

    if (result.status === 'success') {
        urlSaveModal.style.display = "none";
        alert("URL配置保存成功!");
    } else {
        alert('URL配置保存失败:' + result.failInfo);
    }
}

async function saveJdbcConfig() {
    let jdbcSaveConfig = {
        aliasesJdbc: document.getElementById(`saveAliasesJdbc`).value.trim(),
        url: jdbcSaveModalJdbcUrlInput.value.trim(),
        jdbcUser: jdbcSaveModalJdbcUserInput.value.trim(),
        jdbcPassword: jdbcSaveModalJdbcPasswordInput.value.trim(),
        jdbcType: jdbcSaveModalJdbcTypeInput.value.trim(),
        idUrlUser: (jdbcSaveModalJdbcUrlInput.value + "|" + jdbcSaveModalJdbcUserInput.value + "|" + jdbcSaveModalJdbcTypeInput.value).trim()
    };

    const jdbcSaveResponse = await fetch('/api/jdbc-save-configuration', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        body: JSON.stringify(jdbcSaveConfig)
    });

    const result = await jdbcSaveResponse.json();
    if (result.status === 'success') {
        jdbcSaveModal.style.display = "none";
        alert("JDBC配置保存成功!");
    } else {
        alert('JDBC配置保存失败:' + result.failInfo);
    }
}
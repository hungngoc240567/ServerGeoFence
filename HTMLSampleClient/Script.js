
const urlAddGeoFence = "http://127.0.0.1:8080/api/v1/geoFence";
// cai nay la ma loi goi tin 
// neu truong hop server check 
// ma ma loi khac success thi 
// se tuy tinh hinh ma hanh dong
// cai nay server va client se
// thong nhat voi nhau
const ERROR_CODE_ADD_GEO_FENCE = {
    SUCCESS: 0, // thanh cong
    FAIL_LOW_POINT: 3, // that bai do so diem it hon 3
    FAIL_INTERCEPT_POINT: 2, // that bai do hai canh khong ke bi giao nhau
}

// cai nay la khi Hieu gui list point
// co so point be hon 2
const pointsDataWrong3 = {
    points: [
        {x: 1.2, y: 1.3},
        {x: 4, y: 2.25}
    ]
}

// cai nay la khi Hieu gui list point 
// ma co canh bi giao nhau
const pointsDataWrong2 = {
    points: [
        {x: 1, y: 1},
        {x: 2, y: 2},
        {x: 2, y: 1},
        {x: 1, y: 2}
    ]
}

// cai nay la khi Hieu gui list point dung
const pointsDataRight = {
    points: [
        {x: 1129.4839910164865, y: 717.0239472734345}, 
        {x: 1204.3057726900788, y: 777.4224592280647}, 
        {x: 1192.8472643840926, y: 876.8782864836593}, 
        {x: 1124.971803480912, y: 965.6398558826243}, 
        {x: 1070.3430395226508, y: 841.8592692271668}, 
        {x: 1020.8430993924236, y: 752.9294574707204}
    ]
}


function onReceiveAddGeoFencePacket(data){
    switch(data.errCode){
        case ERROR_CODE_ADD_GEO_FENCE.FAIL_LOW_POINT:{
            alert("Number point of geo fence is too low, need greater than 3!");
            break;
        }
        case ERROR_CODE_ADD_GEO_FENCE.FAIL_INTERCEPT_POINT:{
            alert("Some edges that are not adjust was intercept!");
            break;
        }
        case ERROR_CODE_ADD_GEO_FENCE.SUCCESS:{
            alert("Create geo fence success, your geo fence id is" + data.idGeoFence +"\n" + JSON.stringify(data.listGeoFencePoint));
            break;
        }
    }
}


function comonSendPacket(_url, _type, _data, _callBack){
    _data = _data || {};
    _type = _type || "GET";
    $.ajax({
        type: _type,
        url: _url,
        data: JSON.stringify(_data),
        contentType: "application/json",
        dataType: "json",
        success: function(data){
            console.log("co vao day ko 1")
            _callBack && _callBack(data)
        },
        failure: function(errMsg) {
            console.log("Co vao day ko")
            alert(errMsg);
        }
  });
}


function sendWrongAddGeoFenceWithLowPoint(){
    let _data = pointsDataWrong3;
    comonSendPacket(urlAddGeoFence, 'POST', _data, onReceiveAddGeoFencePacket);
}

function sendWrongAddGeoFenceWithEdgeIntercept(){
    let _data = pointsDataWrong2;
    comonSendPacket(urlAddGeoFence, 'POST', _data, onReceiveAddGeoFencePacket);
}

function sendRightAddGeoFence(){
    let _data = pointsDataRight;
    comonSendPacket(urlAddGeoFence, 'POST', _data, onReceiveAddGeoFencePacket);
}
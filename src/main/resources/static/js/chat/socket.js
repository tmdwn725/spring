'use strict';

// document.write("<script src='jquery-3.6.1.js'></script>")
/*document.write("<script\n" +
    "  src=\"https://code.jquery.com/jquery-3.6.1.min.js\"\n" +
    "  integrity=\"sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=\"\n" +
    "  crossorigin=\"anonymous\"></script>")*/


var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var socket = new SockJS('/ws-stomp');
const stompClient = Stomp.over(socket);
stompClient.connect({}, onConnected, onError);
//var stompClient = null;
var memberSeq = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

// 파라미터 가져오기
const url = new URL(location.href).searchParams;
const clubSeq = url.get('clubSeq');
const myClubInfoSeq = document.getElementById("myClubInfoSeq").value;
memberSeq = url.get('memberSeq');
var senderId = document.getElementById("senderId").value;
var senderNm = document.getElementById("senderNm").value;

$(document).ready(function(){
  // username 중복 확인
  //isDuplicateName();

  // usernamePage 에 hidden 속성 추가해서 가리고
  // chatPage 를 등장시킴
  //usernamePage.classList.add('hidden');
  //chatPage.classList.remove('hidden');

  // 연결하고자하는 Socket 의 endPoint
  var socket = new SockJS('/ws-stomp');
  stompClient = Stomp.over(socket);

  stompClient.connect({}, onConnected, onError);

  //event.preventDefault();
});

function onConnected() {

    // sub 할 url => /sub/chat/room/clubSeq 로 구독한다
    stompClient.subscribe('/sub/chat/room/' + clubSeq, onMessageReceived);

    // 서버에 username 을 가진 유저가 들어왔다는 것을 알림
    // /pub/chat/enterUser 로 메시지를 보냄
    stompClient.send("/pub/chat/enterUser",
        {},
        JSON.stringify({
            "clubSeq": clubSeq,
            "clubInfoSeq" : myClubInfoSeq,
            sender: senderNm,
            senderId: senderId,
            type: 'ENTER'
        })
    )
}

// 유저 닉네임 중복 확인
function isDuplicateName() {

    $.ajax({
        type: "GET",
        url: "/chat/duplicateName",
        data: {
            "username": username,
            "clubInfoSeq": myClubInfoSeq
        },
        success: function (data) {
            console.log("함수 동작 확인 : " + data);

            username = data;
        }
    })

}

// 유저 리스트 받기
// ajax 로 유저 리스를 받으며 클라이언트가 입장/퇴장 했다는 문구가 나왔을 때마다 실행된다.
function getUserList() {
/*
    const $list = $("#list");

    $.ajax({
        type: "GET",
        url: "/chat/userlist",
        data: {
            "roomId": roomId
        },
        success: function (data) {
            var users = "";
            for (let i = 0; i < data.length; i++) {
                //console.log("data[i] : "+data[i]);
                users += "<li class='dropdown-item'>" + data[i] + "</li>"
            }
            $list.html(users);
        }
    })
    */
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

// 메시지 전송때는 JSON 형식을 메시지를 전달한다.
function sendMessage(event) {
    var messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        var chatMessage = {
            "clubSeq" : clubSeq,
            "clubInfoSeq": myClubInfoSeq,
            "memberSeq": memberSeq,
            sender: senderNm,
            message: messageInput.value,
            type: 'TALK'
        };
        if (stompClient.connected === true) {
            stompClient.send("/pub/chat/sendMessage", {}, JSON.stringify(chatMessage));
        }
        messageInput.value = '';
    }
    event.preventDefault();
}

// 메시지를 받을 때도 마찬가지로 JSON 타입으로 받으며,
// 넘어온 JSON 형식의 메시지를 parse 해서 사용한다.
function onMessageReceived(payload) {
    //console.log("payload 들어오냐? :"+payload);

    var chatList = JSON.parse(payload.body);
    for (var i in chatList){
        var chat = "";
        // 단건
        if(i == 'chatSeq'){
            chat = chatList
        }else{
            var chat = chatList[i];
        }

        // 이전에 전송한 메시지와 같은 경우, 중복 호출 방지
         if (chat.messageId == $('#messageId').val() || chat.message == null) {
           return;
         }else{
           $('#messageId').val(chat.messageId);
         }

        var messageElement = document.createElement('li');
        var sSpaceElement = document.createElement('div');

        if (chat.type === 'ENTER') {  // chatType 이 enter 라면 아래 내용
            messageElement.classList.add('event-message');
            chat.content = chat.sender + chat.message;
            //getUserList();
        } else if (chat.type === 'LEAVE') { // chatType 가 leave 라면 아래 내용
            messageElement.classList.add('event-message');
            chat.content = chat.sender + chat.message;
            //getUserList();
        } else { // chatType 이 talk 라면 아래 내용
            if(senderNm == chat.sender){
                messageElement.classList.add('chat-message','my-chat');
            }else{
                messageElement.classList.add('chat-message','other-chat');
            }
            var iSpaceElement = document.createElement('div');
            var avatarElement = document.createElement('i');
            var avatarText = document.createTextNode(chat.sender);
            avatarElement.appendChild(avatarText);

            avatarElement.style['background-color'] = getAvatarColor(chat.sender);
            // 이름으로 채팅 프로필 위치 비교 다음에 seq로 바꿀예정
            if(senderNm == chat.sender){
                avatarElement.style['left'] = "3%";
            }else{
                avatarElement.style['right'] = "3%";
            }
            iSpaceElement.appendChild(avatarElement);
            messageElement.appendChild(iSpaceElement);


            var usernameElement = document.createElement('span');
            var usernameText = document.createTextNode(chat.sender);

            usernameElement.appendChild(usernameText);
            sSpaceElement.appendChild(usernameElement);
        }

        // 상대방 대화일 경우 우측 정렬
        if(senderNm != chat.sender){
            sSpaceElement.style['text-align'] = "right";
            sSpaceElement.style['padding-right'] = "50px";
        }

        var contentElement = document.createElement('p');

        var messageText = document.createTextNode(chat.message);

        contentElement.appendChild(messageText);

        sSpaceElement.appendChild(contentElement);
        messageElement.appendChild(sSpaceElement);
        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }
}


function getAvatarColor(messageSender) {
    var hash = 0;
    if (messageSender != null){
        for (var i = 0; i < messageSender.length; i++) {
            hash = 31 * hash + messageSender.charCodeAt(i);
        }
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

//usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
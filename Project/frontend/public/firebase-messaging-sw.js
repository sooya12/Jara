// Give the service worker access to firebase messaging.
// Note that you can only use Firebase Messaging here, other Firebase libraries
// are not available in the service worker.
importScripts('https://www.gstatic.com/firebasejs/7.17.1/firebase-app.js')
importScripts('https://www.gstatic.com/firebasejs/7.17.1/firebase-messaging.js')

// Initialize the Firebase app in the service worker by passing in the
// messagingSenderId
firebase.initializeApp({
    apiKey: "AIzaSyD6kh6dVNW_ORNL5bhPNj9aaZ6UTPD63R0",
    projectId: "jara-a6849",
    messagingSenderId: "732221422294",
    appId: "1:732221422294:web:416988fd3e52691af72777",
})

// Retrieve an instance of Firebase Messaging so that it can handle background
// messages
const messaging = firebase.messaging()

// 백그라운드 상태에서 받은 알림 처리
messaging.setBackgroundMessageHandler((payload) => {
    console.log('[firebase-messaging-sw.js] Received background message ', payload)
    // Customize notification here
    const notificationTitle = 'Background Message Title'
    const notificationOptions ={
        body: 'Background Message body',
        icon:'/firebase-logo.png'
    }

    return self.registration.showNotification(notificationTitle, notificationOptions)
})

// importScripts의 스크립트 버전과 pakcage.json에 명시된 firebase 의존성 버전이 일치하는지 확인
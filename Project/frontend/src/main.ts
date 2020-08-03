import firebase from 'firebase/app'
import 'firebase/messaging'

const firebaseConfig = {
    apiKey: "AIzaSyD6kh6dVNW_ORNL5bhPNj9aaZ6UTPD63R0",
    authDomain: "jara-a6849.firebaseapp.com",
    databaseURL: "https://jara-a6849.firebaseio.com",
    projectId: "jara-a6849",
    storageBucket: "jara-a6849.appspot.com",
    messagingSenderId: "732221422294",
    appId: "1:732221422294:web:416988fd3e52691af72777",
    measurementId: "G-0GRH2MEHNH"
}

firebase.initializeApp(firebaseConfig)

const messaging = firebase.messaging()

messaging.usePublicVapidKey('BMteNeHV1uaQITpVbLiUvM73PXWfFGDvyDv06QZn9wn9knSirhDXUtujGcArzVq8A3KfA1sAj6bg99Cc3BDwp6c')

// 알림 수신을 위한 사용자 권한 요청
Notification.requestPermission()
    .then((permission) => {
        console.log('permission', permission)
        if (permission !== 'granted') {
            alert('알림을 허용해주세요')
        }
    })

// TODO: Send token to server for send notification
messaging.getToken()
    .then(console.log)

// Handle received push notification at foreground
messaging.onMessage(payload => {
    console.log(payload)
    alert(payload.data.message)
})
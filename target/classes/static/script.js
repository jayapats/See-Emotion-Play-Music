const video = document.getElementById('video')

Promise.all([
  faceapi.nets.tinyFaceDetector.loadFromUri('/models'),
  faceapi.nets.faceLandmark68Net.loadFromUri('/models'),
  faceapi.nets.faceRecognitionNet.loadFromUri('/models'),
  faceapi.nets.faceExpressionNet.loadFromUri('/models')
]).then(startVideo)

function startVideo() {
  navigator.getUserMedia(
    { video: {} },
    stream => video.srcObject = stream,
    err => console.error(err)
  )
}
function analyze( emotion,  c){
console.log(emotion)
clearInterval(c)
}

video.addEventListener('play', () => {
  const canvas = faceapi.createCanvasFromMedia(video)
  document.body.append(canvas)
  const displaySize = { width: video.width, height: video.height }
  faceapi.matchDimensions(canvas, displaySize)
 var x=  setInterval(async () => {
    const detections = await faceapi.detectAllFaces(video, new faceapi.TinyFaceDetectorOptions()).withFaceLandmarks().withFaceExpressions()
    const resizedDetections = faceapi.resizeResults(detections, displaySize)
    const exp = resizedDetections.at(0)
 if (typeof exp !== 'undefined'){

  var angry = exp.expressions.angry
  var disgusted= exp.expressions.disgusted
  var fear  = exp.expressions.fearful
  var happy = exp.expressions.happy
  var neutral = exp.expressions.neutral
  var sad = exp.expressions.sad
  
  var reaction = Math.max(angry,disgusted,fear,happy,neutral,sad) 
  var htmlre =""
  if(reaction ===angry)
	  htmlre = "angry"
	  if(reaction ===disgusted)
		  htmlre = "disgusted"
		  if(reaction ===fear)
			  htmlre = "fear"
			  if(reaction ===happy)
				  htmlre = "happy"
				  if(reaction ===neutral)
					  htmlre = "neutral"
					  if(reaction ===sad)
						  htmlre = "sad"

    analyze(exp.expressions,x)
    document.getElementById("emotion").value = htmlre;
  

  }
  
    /*const exp = resizedDetections.at(0).expressions
    canvas.getContext('2d').clearRect(0, 0, canvas.width, canvas.height)
    faceapi.draw.drawDetections(canvas, resizedDetections)
    faceapi.draw.drawFaceLandmarks(canvas, resizedDetections)
    faceapi.draw.drawFaceExpressions(canvas, resizedDetections)*/
  }, 1000
  )
})
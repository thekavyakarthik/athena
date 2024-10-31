package com.example.tuesday.record

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File
import java.io.FileOutputStream

class AndroidAudioRecorder(
    private val context: Context
): AudioRecorder {

    private var recorder: MediaRecorder? = null // global variable

    private fun createRecorder(): MediaRecorder {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder() // old way of initializing the media recorder; deprecated in newer versions
    }

    override fun start(outputFile: File) {
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC) // set phones microphone to audio source
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) // this should be mp3 or mp4, what about wav format??
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC) // impacts voice quality
            setOutputFile(FileOutputStream(outputFile).fd)

            prepare() // prepare to record
            start() // start recording

            recorder = this
        }
    }

    override fun stop() {
        recorder?.stop()
        recorder?.reset()
        recorder = null
    }
}
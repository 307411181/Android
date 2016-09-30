package com.unihiltop.xiangliao.call.utils;

import android.content.Context;
import android.media.AudioManager;

public class SpeakerManager {

//	private int currVolume;

	// 打开扬声器
	public void openSpeaker(Context context) {

		try {
			AudioManager audioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
//			audioManager.setMode(AudioManager.ROUTE_SPEAKER);
//			currVolume = audioManager
//					.getStreamVolume(AudioManager.STREAM_VOICE_CALL);

			if (!audioManager.isSpeakerphoneOn()) {
				audioManager.setSpeakerphoneOn(true);

//				audioManager
//						.setStreamVolume(
//								AudioManager.STREAM_VOICE_CALL,
//								audioManager
//										.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL),
//								AudioManager.STREAM_VOICE_CALL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 关闭扬声器
	public void closeSpeaker(Context context) {
		try {
			AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
			if (audioManager != null) {
				if (audioManager.isSpeakerphoneOn()) {
					audioManager.setSpeakerphoneOn(false);
//					audioManager.setStreamVolume(
//							AudioManager.STREAM_VOICE_CALL, currVolume,
//							AudioManager.STREAM_VOICE_CALL);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Toast.makeText(context,"扬声器已经关闭",Toast.LENGTH_SHORT).show();
	}
}

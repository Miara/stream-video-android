package io.getstream.video.android.service

import android.content.Context
import io.getstream.video.android.StreamVideo

public interface StreamCallService {

    public fun getStreamCalls(context: Context): StreamVideo

}
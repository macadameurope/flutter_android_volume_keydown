package dev.darttools.flutter_android_volume_keydown;

import android.view.KeyEvent;
import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;

/** FlutterAndroidVolumeKeydownPlugin */
public class FlutterAndroidVolumeKeydownPlugin implements FlutterPlugin, EventChannel.StreamHandler, ActivityAware {
    private EventChannel channel;
    private EventChannel.EventSink eventSink;
    private ActivityPluginBinding activityPluginBinding;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),
                "dart-tools.dev/flutter_android_volume_keydown");
        channel.setStreamHandler(this);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        if (channel != null) {
            channel.setStreamHandler(null);
            channel = null;
        }
        eventSink = null;
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        this.eventSink = events;
    }

    @Override
    public void onCancel(Object arguments) {
        this.eventSink = null;
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        this.activityPluginBinding = binding;
        binding.addOnKeyListener(this::handleKey);
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        if (activityPluginBinding != null) {
            activityPluginBinding.removeOnKeyListener(this::handleKey);
            activityPluginBinding = null;
        }
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        onAttachedToActivity(binding);
    }

    @Override
    public void onDetachedFromActivity() {
        if (activityPluginBinding != null) {
            activityPluginBinding.removeOnKeyListener(this::handleKey);
            activityPluginBinding = null;
        }
    }

    private boolean handleKey(int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_DOWN || eventSink == null)
            return false;

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            eventSink.success(true); // volume down
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            eventSink.success(false); // volume up
            return true;
        }

        return false;
    }
}

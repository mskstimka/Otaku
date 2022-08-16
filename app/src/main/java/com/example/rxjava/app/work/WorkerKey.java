package com.example.rxjava.app.work;

import androidx.work.ListenableWorker;

import com.google.android.datatransport.runtime.dagger.MapKey;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@MapKey
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkerKey {
    Class<? extends ListenableWorker> value();
}
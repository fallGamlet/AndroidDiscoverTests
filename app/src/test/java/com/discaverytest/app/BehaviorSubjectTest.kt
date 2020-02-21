package com.discaverytest.app

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


class BehaviorSubjectTest {

    @Test
    fun testNextAfterError() {
        val subject = BehaviorSubject.create<String>()

        var value: String? = null

        subject
            .doOnNext { value = it }
            .subscribe({}, {}, {})

        subject.onNext("value 1")
        subject.onError(RuntimeException("error 1"))
        subject.onNext("value 2")

        assertEquals("value 1", value)
    }

    @Test
    fun testNextAfterComplete() {
        val subject = BehaviorSubject.create<String>()

        var value: String? = null

        subject
            .doOnNext { value = it }
            .subscribe({}, {}, {})

        subject.onNext("value 1")
        subject.onComplete()
        subject.onNext("value 2")

        assertEquals("value 1", value)
    }

    @Test
    fun testManyErrors() {
        val subject = BehaviorSubject.create<String>()

        var error: Throwable? = null

        subject
            .doOnError { error = it }
            .subscribe({}, {}, {})

        subject.onError(RuntimeException("error 1"))
        subject.onError(RuntimeException("error 2"))

        assertEquals("error 1", error?.message)
    }

    @Test
    fun testStopChangesAfterUnsubscribe() {
        val subject = BehaviorSubject.create<String>()

        var value: String? = null

        val disposable = subject
            .doOnNext { value = it }
            .subscribe({}, {}, {})

        subject.onNext("value 1")
        disposable.dispose()
        subject.onNext("value 2")

        assertEquals("value 1", value)
    }

    @Test
    fun testResume() {
        val subject = BehaviorSubject.create<String>()

        var value: String? = null

        val disposable = subject.subscribe({}, {}, {})

        subject.onNext("value 1")
        disposable.dispose()

        subject
            .doOnNext { value = it }
            .subscribe({}, {}, {})

        subject.onNext("value 2")

        assertEquals("value 2", value)
    }

    @Test
    fun testSubscribeAfterEmit() {
        val subject = BehaviorSubject.create<String>()

        var value: String? = null

        subject.onNext("value 1")

        subject
            .doOnNext { value = it }
            .subscribe({}, {}, {})

        assertEquals("value 1", value)
    }

    @Test
    fun testThrowableSubject() {
        val subject = BehaviorSubject.create<Throwable>()

        var value: Throwable? = null

        subject
            .doOnNext { value = it }
            .subscribe({}, {}, {})

        subject.onNext(RuntimeException("value 1"))
        subject.onNext(RuntimeException("value 2"))

        assertEquals("value 2", value?.message)
        assertEquals("value 2", subject.value?.message)
    }

    @Test
    fun testOptionalSubject() {
        val subject = BehaviorSubject.create<Optional<String>>()

        var value1 = "init"
        var value2 = "init"

        val observable = subject
            .filter { it.isPresent }
            .map {
                subject.onNext(Optional.empty())
                it.get()
            }

        subject.onNext(Optional.of("value 1"))

        observable
            .doOnNext { value1 = it }
            .subscribe({}, {}, {})
            .dispose()

        observable
            .doOnNext { value2 = it }
            .subscribe({}, {}, {})
            .dispose()

        subject.onNext(Optional.of("value 2"))

        assertEquals("value 1", value1)
        assertEquals("init", value2)
        assertEquals("value 2", subject.value?.get())
    }

    @Test
    fun testMerge() {
        val subject1 = BehaviorSubject.create<String>()
        val subject2 = BehaviorSubject.create<String>()

        val items = mutableListOf<String>()

        val observable = Observable.empty<String>()
            .mergeWith(subject1)
            .mergeWith(subject2)

        subject1.onNext("subject1 value1")
        subject2.onNext("subject2 value2")

        observable
            .doOnNext { items.add(it) }
            .subscribe({}, {}, {})

        subject1.onNext("subject1 value3")
        subject2.onNext("subject2 value4")

        assertEquals("subject1 value1", items[0])
        assertEquals("subject2 value2", items[1])
        assertEquals("subject1 value3", items[2])
        assertEquals("subject2 value4", items[3])
    }

}

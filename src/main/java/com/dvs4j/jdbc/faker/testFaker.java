package com.dvs4j.jdbc.faker;

import org.h2.store.fs.FakeFileChannel;

import com.github.javafaker.Faker;

public class testFaker {
	public static void main(String[] args) {
		Faker faker= new Faker();
		System.out.println(faker.name().firstName());
		System.out.println(faker.name().lastName());
		System.out.println(faker.dragonBall().character());
		System.out.println(faker.gameOfThrones().character());
	}

}

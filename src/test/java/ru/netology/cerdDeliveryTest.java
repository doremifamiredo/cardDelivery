package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

class cerdDeliveryTest {
    private String getDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    public void MustBookAppointment() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Краснодар");
        String planeDate = getDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planeDate);
        $("[data-test-id='name'] input").setValue("Имя Фамилия");
        $("[data-test-id='phone'] input").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planeDate));
    }

    @Test
    public void MustBookAppointmentUsingButtons() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Рд");
        $$(".menu-item").find(Condition.text("Ростов")).click();
        $(".icon_name_calendar").click();
        $$(".calendar__day").find(Condition.text(getDate(4,"dd"))).click();
        String planeDate = $("[data-test-id='date'] input").getValue();
        $("[data-test-id='name'] input").setValue("Имя Фамилия");
        $("[data-test-id='phone'] input").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planeDate));
    }
}

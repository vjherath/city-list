package com.example.citylist.repo;

import com.example.citylist.model.City;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;


import javax.persistence.EntityTransaction;
import javax.transaction.Transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class CityRepoTest {

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Before
    public void setup(){

        EntityTransaction transaction = mock(EntityTransaction.class);
        try{

            transaction.begin();

            City colombo = new City();
            colombo.setName("Colombo");
            entityManager.persist(colombo);

            City nittambuwa = new City();
            nittambuwa.setName("Nittambuwa");
            entityManager.persist(nittambuwa);

            City kandy = new City();
            kandy.setName("Kandy");
            entityManager.persist(kandy);

            City negombo = new City();
            negombo.setName("NegomBo");
            entityManager.persist(negombo);

            City galle = new City();
            galle.setName("Galle");
            entityManager.persist(galle);

            transaction.commit();

        } finally {
            entityManager.flush();
        }

    }

    @Test
    public void findByNameLikeIgnoreCase (){

        // setup required mocks
        String searchText = "%bo%";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

        // test
        Page<City> cityPage = cityRepo.findByNameLikeIgnoreCase(searchText, pageable);

        assertThat(cityPage).isNotNull();
        assertThat(cityPage.getContent()).isNotEmpty();
        assertEquals(cityPage.getContent().size(), 2);

//        assertThat(cityPage.getContent(), containsInAnyOrder(
//                hasProperty("name", is("Colombo")),
//                hasProperty("name", is("NegomBo"))
//        ));
    }

    @Test
    public void findById(){

        // setup required mocks
        long id = 1;
        long db_id;

        // test
        City city = cityRepo.findById(id).orElse(null);

        assertThat(city).isNotNull();
        assertThat(city.getId()).isNotNull();
        db_id = city.getId();
        assertEquals(db_id, id);

    }
}

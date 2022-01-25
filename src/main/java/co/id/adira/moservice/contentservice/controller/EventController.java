package co.id.adira.moservice.contentservice.controller;

import co.id.adira.moservice.contentservice.model.content.Event;
import co.id.adira.moservice.contentservice.util.BaseResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @GetMapping(path = "/events")
    public ResponseEntity<Object> getEvent(
            @RequestParam(required = false, defaultValue = "", name = "active") final String active
    ) {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Event> cr = cb.createQuery(Event.class);
        Root<Event> root = cr.from(Event.class);
        cr.select(root);

        List<Predicate> predicates = new ArrayList<Predicate>();

        Predicate isNotDeleted = cb.equal(root.get("isDeleted"), false);
        predicates.add(isNotDeleted);

        if (!active.isEmpty()) {
            if (active.equals("1") || active.equals("0")) {
                boolean filterIsActive = active.equals("1") ? true : false;
                Predicate isActivePredicate = cb.equal(root.get("isActive"), filterIsActive);
                predicates.add(isActivePredicate);
                if (active.equals("1")) {
                    Predicate isActiveDateGtePredicate
                            = cb.greaterThanOrEqualTo(cb.currentDate(), root.get("startDate"));
                    Predicate isActiveDateLtePredicate
                            = cb.lessThanOrEqualTo(cb.currentDate(), root.get("endDate"));
                    Predicate isActiveDatePredicate
                            = cb.and(isActiveDateGtePredicate, isActiveDateLtePredicate);
                    predicates.add(isActiveDatePredicate);
                }
            }
        }

        Predicate[] predicateArray = predicates.toArray(new Predicate[0]);
        cr.where(predicateArray);

        Query<Event> query = session.createQuery(cr);
        List<Event> results = query.getResultList();

        return BaseResponse.jsonResponse(HttpStatus.OK, true, HttpStatus.OK.toString(), results);
    }

}

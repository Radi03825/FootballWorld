package bg.softuni.FootballWorld.repository;

import bg.softuni.FootballWorld.model.dto.SearchPlayerDTO;
import bg.softuni.FootballWorld.model.entity.PlayerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PlayerSpecification implements Specification<PlayerEntity> {

    private final SearchPlayerDTO searchPlayerDTO;

    public PlayerSpecification(SearchPlayerDTO searchPlayerDTO) {
        this.searchPlayerDTO = searchPlayerDTO;
    }

    @Override
    public Predicate toPredicate(Root<PlayerEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate p = criteriaBuilder.conjunction();

        if (searchPlayerDTO.getFirstName() != null && !searchPlayerDTO.getFirstName().isEmpty()) {
            p.getExpressions().add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("firstName"), searchPlayerDTO.getFirstName()))
            );
        }

        if (searchPlayerDTO.getMinPrice() != null) {
            p.getExpressions().add(
                    criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), searchPlayerDTO.getMinPrice()))
            );
        }

        if (searchPlayerDTO.getMaxPrice() != null) {
            p.getExpressions().add(
                    criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("price"), searchPlayerDTO.getMaxPrice()))
            );
        }

        return p;
    }
}

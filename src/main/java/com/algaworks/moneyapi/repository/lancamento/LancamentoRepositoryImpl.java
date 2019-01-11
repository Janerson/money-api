package com.algaworks.moneyapi.repository.lancamento;

import com.algaworks.moneyapi.model.Lancamento;
import com.algaworks.moneyapi.model.Lancamento_;
import com.algaworks.moneyapi.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRespositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {


        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarPredicates(filter, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteria);

        int total = query.getResultList().size();

        adicionarRestricaoDePagina(query , pageable);

        return new PageImpl<>(query.getResultList(), pageable,total);
    }

    private Long total(LancamentoFilter filter) {
        return null;
    }

    private void adicionarRestricaoDePagina(TypedQuery<Lancamento> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistroPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistroPorPagina);

    }


    private Predicate[] criarPredicates(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(filter.getDescricao())) {
            predicates.add(builder.like(
                    builder.lower(root.get(Lancamento_.descricao)), "%" + filter.getDescricao() + "%"
            ));
        }

        if (!StringUtils.isEmpty(filter.getDataVencimentoDe())) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoDe())
            );
        }

        if (!StringUtils.isEmpty(filter.getDataVencimentoAte())) {
            predicates.add(
                    builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoAte())
            );
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }


}

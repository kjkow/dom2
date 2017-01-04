package github.kjkow.automaty.excel;


import github.kjkow.kontekst.KontekstZwracany;

/**
 * Created by Kamil.Kowalczyk on 2016-12-07.
 */
public interface IAutomatDoExcela {

    /**
     * zmienia zakresy w arkuszu plus jeden miesiac na zakladce Wydatki zestawienie
     * @param sciezkaDoArkusza
     * return log z migratora
     */
    KontekstZwracany migrujZakresy(String sciezkaDoArkusza);

    /**
     * tworzy nowy nowyArkusz excela w tym samym katalogu co stary, z nowa nazwa
     * @param sciezkaDoArkusza
     * @return
     */
    KontekstZwracany utworzArkuszNaNowyRok(String sciezkaDoArkusza);
}

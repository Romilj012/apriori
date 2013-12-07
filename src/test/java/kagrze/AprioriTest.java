package kagrze;

import java.util.ArrayList;
import java.util.List;

import kagrze.Apriori;
import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;

/**
 * 
 * @author Karol Grzegorczyk
 *
 */
public class AprioriTest {

    @Test
    public void testMineFrequentItemSets() {
        List<List<String>> database = new ArrayList<>();
        database.add(createItemset("A","C"));
        database.add(createItemset("A","B","C","D"));
        database.add(createItemset("A","C","D"));
        Multiset<List<String>> frequentItemsets = new Apriori().mineFrequentItemSets(database, 3);
        
        Multiset<List<String>> expectedItemsets = LinkedHashMultiset.create();
        expectedItemsets.add(createItemset("A"),3);
        expectedItemsets.add(createItemset("C"),3);
        expectedItemsets.add(createItemset("A","C"),3);
        assertEquals(expectedItemsets, frequentItemsets);
    }
    
    @Test
    public void testMineFrequentItemSets2() {
        List<List<String>> database = new ArrayList<>();
        database.add(createItemset("A","C"));
        database.add(createItemset("A","D"));
        database.add(createItemset("A","B","C"));
        database.add(createItemset("A","C","D"));
        Multiset<List<String>> frequentItemsets = new Apriori().mineFrequentItemSets(database, 2);
        
        Multiset<List<String>> expectedItemsets = LinkedHashMultiset.create();
        expectedItemsets.add(createItemset("A"),4);
        expectedItemsets.add(createItemset("C"),3);
        expectedItemsets.add(createItemset("D"),2);
        expectedItemsets.add(createItemset("A","C"),3);
        expectedItemsets.add(createItemset("A","D"),2);
        assertEquals(expectedItemsets, frequentItemsets);
    }
    
    @Test
    public void testMineFrequentItemSets3() {
        List<List<String>> database = new ArrayList<>();
        database.add(createItemset("A","B","C","D","E"));
        database.add(createItemset("B","C","D","E","F"));
        database.add(createItemset("C","D","E","F","G"));
        Multiset<List<String>> frequentItemsets = new Apriori().mineFrequentItemSets(database, 3);
        
        Multiset<List<String>> expectedItemsets = LinkedHashMultiset.create();
        expectedItemsets.add(createItemset("C"),3);
        expectedItemsets.add(createItemset("D"),3);
        expectedItemsets.add(createItemset("E"),3);
        expectedItemsets.add(createItemset("C","D"),3);
        expectedItemsets.add(createItemset("C","E"),3);
        expectedItemsets.add(createItemset("D","E"),3);
        expectedItemsets.add(createItemset("C","D","E"),3);
        assertEquals(expectedItemsets, frequentItemsets);
    }
    
    @Test
    public void testJoin() {
        List<List<String>> frequent1Itemsets = new ArrayList<>();
        frequent1Itemsets.add(createItemset("A"));
        frequent1Itemsets.add(createItemset("B"));
        frequent1Itemsets.add(createItemset("C"));
        frequent1Itemsets.add(createItemset("D"));
        List<List<String>> candidates = new Apriori().aprioriGen(frequent1Itemsets);
        List<List<String>> expectedCandidates = new ArrayList<>();
        expectedCandidates.add(createItemset("A", "B"));
        expectedCandidates.add(createItemset("A", "C"));
        expectedCandidates.add(createItemset("A", "D"));
        expectedCandidates.add(createItemset("B", "C"));
        expectedCandidates.add(createItemset("B", "D"));
        expectedCandidates.add(createItemset("C", "D"));
        assertEquals(expectedCandidates, candidates);
    }
    
    @Test
    public void testJoin2() {
        List<List<Integer>> frequent3Itemsets = new ArrayList<>();
        frequent3Itemsets.add(createItemset(1,2,3));
        frequent3Itemsets.add(createItemset(1,2,4));
        frequent3Itemsets.add(createItemset(1,3,4));
        frequent3Itemsets.add(createItemset(1,3,5));
        frequent3Itemsets.add(createItemset(2,3,4));
        List<List<Integer>> candidates = new Apriori().aprioriGen(frequent3Itemsets);
        List<List<Integer>> expectedCandidates = new ArrayList<>();
        expectedCandidates.add(createItemset(1,2,3,4));
        assertEquals(expectedCandidates, candidates);
    }
    
    @Test
    public void testGenerateAllCombinationsWithoutRepetitions() {
        List<String> itemset = new ArrayList<>();
        itemset.add("A");
        itemset.add("B");
        itemset.add("C");
        itemset.add("D");
        itemset.add("E");
        List<List<String>> combinations = new Apriori().generateAllCombinationsWithoutRepetitions(itemset,3);
        List<List<String>> expectedCombinations = new ArrayList<>();
        expectedCombinations.add(createItemset("A","B","C"));
        expectedCombinations.add(createItemset("A","B","D"));
        expectedCombinations.add(createItemset("A","B","E"));
        expectedCombinations.add(createItemset("A","C","D"));
        expectedCombinations.add(createItemset("A","C","E"));
        expectedCombinations.add(createItemset("A","D","E"));
        expectedCombinations.add(createItemset("B","C","D"));
        expectedCombinations.add(createItemset("B","C","E"));
        expectedCombinations.add(createItemset("B","D","E"));
        expectedCombinations.add(createItemset("C","D","E"));
        assertEquals(expectedCombinations, combinations);
    }
    
    private <E> List<E> createItemset(E ... items) {
        List<E> itemset = new ArrayList<>();
        for (E item : items)
            itemset.add(item);
        return itemset;
    }
}

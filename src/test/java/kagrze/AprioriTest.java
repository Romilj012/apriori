package kagrze;

import java.util.ArrayList;
import java.util.List;

import kagrze.Apriori;
import static org.junit.Assert.*;

import org.junit.Test;

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
		Iterable<List<String>> frequentItemsets = new Apriori().mineFrequentItemSets(database, 3);
		
		List<List<String>> expectedItemsets = new ArrayList<>();
		expectedItemsets.add(createItemset("A"));
		expectedItemsets.add(createItemset("C"));
		expectedItemsets.add(createItemset("A","C"));
		assertEquals(expectedItemsets, frequentItemsets);
	}
	
	@Test
	public void testMineFrequentItemSets2() {
		List<List<String>> database = new ArrayList<>();
		database.add(createItemset("A","C"));
		database.add(createItemset("A","D"));
		database.add(createItemset("A","B","C"));
		database.add(createItemset("A","C","D"));
		Iterable<List<String>> frequentItemsets = new Apriori().mineFrequentItemSets(database, 2);
		
		List<List<String>> expectedItemsets = new ArrayList<>();
		expectedItemsets.add(createItemset("A"));
		expectedItemsets.add(createItemset("C"));
		expectedItemsets.add(createItemset("D"));
		expectedItemsets.add(createItemset("A","C"));
		expectedItemsets.add(createItemset("A","D"));
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

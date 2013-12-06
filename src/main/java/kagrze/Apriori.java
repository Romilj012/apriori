package kagrze;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

/**
 * Implementation of the Apriori algorithm introduced in the article 
 * "Fast algorithms for mining association rules" by R. Agrawal and R. Srikant
 * @author Karol Grzegorczyk
 *
 */
public class Apriori {

	/**
	 * It is assumed that items within a transaction are sorted in lexicographic order.
	 * @param transactions
	 * @param support
	 * @return
	 */
	public <E extends Comparable> List<List<E>> mineFrequentItemSets(List<List<E>> transactions, int support) {
		List<List<E>> frequentItemsets = genFrequent1Itemsets(transactions, support);

		List<List<E>> candidatesForFrequentKItemsets = aprioriGen(frequentItemsets);

		for(int size=2; candidatesForFrequentKItemsets.size() > 0; size++) {		
			List<List<E>> frequentKItemsets = getFrequentKItemsets(
					transactions, support, candidatesForFrequentKItemsets, size);
			frequentItemsets.addAll(frequentKItemsets);
			candidatesForFrequentKItemsets = aprioriGen(frequentKItemsets);
		}
		
		return frequentItemsets;
	}

	private <E extends Comparable> List<List<E>> getFrequentKItemsets(
			List<List<E>> transactions, int support, List<List<E>> candidatesForFrequentKItemsets, int size) {
		Multiset<List<E>> allKItemsets = HashMultiset.create();
		for (List<E> transaction : transactions) {
			List<List<E>> combinations = generateAllCombinationsWithoutRepetitions(transaction, size);
			for (List<E> candidateForFrequentKItemset : candidatesForFrequentKItemsets) {
				for (List<E> combination : combinations) {
					if (combination.equals(candidateForFrequentKItemset)) {
						allKItemsets.add(candidateForFrequentKItemset);
						break;
					}
				}
			}
		}
		List<List<E>> frequentKItemsets = new ArrayList<>();
		for (List<E> itemset : allKItemsets.elementSet()) {
			int count = allKItemsets.count(itemset);
			if (count >= support)
				frequentKItemsets.add(itemset);
		}
		return frequentKItemsets;
	}

	private <E extends Comparable> List<List<E>> genFrequent1Itemsets(List<List<E>> transactions, int support) {
		SortedMultiset<E> all1Itemsets = TreeMultiset.create();
		for (List<E> transaction : transactions)
			for (E item : transaction)
				all1Itemsets.add(item);
		List<List<E>> frequent1Itemsets = new ArrayList<>();
		for (E item : all1Itemsets.elementSet()) {
			int count = all1Itemsets.count(item);
			if(count >= support) {
				List<E> itemset = new ArrayList<>();
				itemset.add(item);
				frequent1Itemsets.add(itemset);
			}
		}
		return frequent1Itemsets;
	}

	/**
	 * This function contains two steps: join step and prune step
	 * @param frequent1Itemsets
	 * @return
	 */
	<E> List<List<E>> aprioriGen(List<List<E>> frequentItemsets) {
		List<List<E>> candidates = new ArrayList<>();

		for (int i=0; i<frequentItemsets.size(); i++) {
			for (int j=i+1; j<frequentItemsets.size(); j++) {
				List<E> lhs = frequentItemsets.get(i);
				List<E> rhs = frequentItemsets.get(j);
				if (lhs.size()==1 || lhs.subList(0, lhs.size()-1).equals(rhs.subList(0,rhs.size()-1))) {
					List<E> candidate = new ArrayList<>(lhs);
					//join
					candidate.add(rhs.get(rhs.size()-1));
					//prune
					if(!hasInfrequentSubset(candidate,frequentItemsets))
						candidates.add(candidate);
				}
			}
		}
		return candidates;
	}
	
	private <E> boolean hasInfrequentSubset(List<E> candidate, List<List<E>> frequentItemsets) {
		for (List<E> subset : generateAllCombinationsWithoutRepetitions(candidate,candidate.size()-1)) {
			if (!frequentItemsets.contains(subset)) {
				return true;
			}
		}
		return false;
	}

	<E> List<List<E>> generateAllCombinationsWithoutRepetitions(List<E> itemset, int subsetSize) {
		List<List<E>> combinations = new ArrayList<>();
		List<E> combinationPart = new ArrayList<>();
		combinationsRecurrent(itemset,subsetSize,0,0,combinationPart,combinations);
		return combinations;
	}
	
	private <E> void combinationsRecurrent(List<E> itemset, int subsetSize, int level, int index, List<E> combinationPart, List<List<E>> combinations) {
		if (level == subsetSize) {
			combinations.add(combinationPart);
		} else {
			for(int i = index; i<=itemset.size()-subsetSize+level; i++) {
				List<E> newCombinationPart = new ArrayList<>(combinationPart);
				newCombinationPart.add(itemset.get(i));
				combinationsRecurrent(itemset, subsetSize, level+1, i+1, newCombinationPart, combinations);
			}
		}
	}
}
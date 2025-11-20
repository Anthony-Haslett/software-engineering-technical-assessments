import { fetchResultData, fetchCandidateData } from '../fakeAPI'; // Let's imagine this is an external service that we are calling via https

async function fetchResults() {
  const results = await fetchResultData();
  const candidateData = fetchCandidateData();

  // Merge candidate names into results
  const resultsWithNames = results.results.map(result => {
    const candidate = candidateData.find(c => c.id === result.candidateId);
    return {
      ...result,
      candidateName: candidate ? candidate.name : `Candidate ${result.candidateId}`
    };
  });

  return {
    ...results,
    results: resultsWithNames
  };
}

export default fetchResults;

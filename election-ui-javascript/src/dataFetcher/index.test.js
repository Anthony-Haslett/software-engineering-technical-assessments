import fetchResults from '../dataFetcher';
import { fetchResultData, fetchCandidateData } from '../fakeAPI';

jest.mock('../fakeAPI');

const mockFakeApi = () => {
  fetchResultData.mockImplementationOnce(() => {
    return Promise.resolve({
      isComplete: false,
      results: [
        {
          'party': 'Giraffe Party',
          'candidateId': 2,
          'votes': '9900'
        }
      ]
    })
  });
  fetchCandidateData.mockImplementation(() => {
    return [
      { id: 1, name: 'Baldrick' },
      { id: 2, name: 'Lord Buckethead' },
      { id: 3, name: 'Count Binface' }
    ];
  });
}

test('returns an Object', async () => {
    mockFakeApi();
    const resultData = await fetchResults();
    expect(typeof resultData).toBe('object');
});

test('response contains a result array', async () => {
  mockFakeApi();
  const resultData = await fetchResults();
  expect(Array.isArray(resultData.results)).toBe(true);
});

test('results include candidate names', async () => {
  mockFakeApi();
  const resultData = await fetchResults();
  expect(resultData.results[0].candidateName).toBe('Lord Buckethead');
});

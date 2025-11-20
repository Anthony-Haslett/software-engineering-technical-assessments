import './Scorecard.css';

function Scorecard({ results, isComplete }) {
  if (!results || results.length === 0) {
    return <div>No results</div>;
  }

  // Calculate the winner - candidate with the most votes
  let winnerIndex = -1;
  if (isComplete && results.length > 0) {
    let maxVotes = -1;
    for (let i = 0; i < results.length; i++) {
      const votes = parseInt(results[i].votes);
      if (votes > maxVotes) {
        maxVotes = votes;
        winnerIndex = i;
      }
    }
  }

  let scores = [];
  for (let i=0; i < results.length; i++) {
    const isWinner = i === winnerIndex;
    scores.push(
      <tr key={i} className={isWinner ? 'winner-row' : ''}>
        <td>{results[i].party}</td>
        <td>{results[i].candidateId}</td>
        <td>{results[i].votes}</td>
      </tr>
    )
  }

  return (
    <div className="Scorecard">
        <table className="Scorecard-table">
          <thead>
            <tr>
              <th>Party</th>
              <th>Candidate</th>
              <th>Votes</th>
            </tr>
          </thead>
          <tbody>
            {scores}
          </tbody>
        </table>
    </div>
  );
}

export default Scorecard;

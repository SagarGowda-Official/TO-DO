import React from "react";

function SummaryButton({ handleSummarize }) {
  return (
    <button onClick={handleSummarize} className="btn btn-success mt-4">
      Summarize & Send to Slack
    </button>
  );
}

export default SummaryButton;

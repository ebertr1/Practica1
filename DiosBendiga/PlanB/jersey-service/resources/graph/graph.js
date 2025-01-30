var nodes = new vis.DataSet([
  {id: 1, label: "Calva"},
  {id: 2, label: "Solana"},
  {id: 3, label: "Pradera"},
  {id: 4, label: "Horizonte"},
  {id: 5, label: "Aurora"},
  {id: 6, label: "Renacer"},
  {id: 7, label: "Riviera"},
  {id: 8, label: "Oasis"},
  {id: 9, label: "Paraíso"},
  {id: 10, label: "Cumbre"},
  {id: 11, label: "Luz"},
  {id: 12, label: "Mar"},
  {id: 13, label: "Cielo"},
]);

var edges = new vis.DataSet([
  {from: 1, to: 2, label: "3935.7463", color: "#2B7CE9"},
  {from: 1, to: 4, label: "5570.222", color: "#2B7CE9"},
  {from: 2, to: 3, label: "2803.9714", color: "#2B7CE9"},
  {from: 3, to: 5, label: "2984.9077", color: "#2B7CE9"},
  {from: 4, to: 6, label: "343.55606", color: "#2B7CE9"},
  {from: 2, to: 4, label: "4567.345", color: "#2B7CE9"},
  {from: 5, to: 7, label: "5000.123", color: "#2B7CE9"},
  {from: 6, to: 9, label: "645.321", color: "#2B7CE9"},
  {from: 7, to: 8, label: "1234.567", color: "#2B7CE9"},
  {from: 8, to: 10, label: "7890.222", color: "#2B7CE9"},
  {from: 9, to: 12, label: "4583.456", color: "#2B7CE9"},
  {from: 10, to: 13, label: "7891.234", color: "#2B7CE9"},
  {from: 1, to: 13, label: "3056.123", color: "#2B7CE9"},
  {from: 2, to: 12, label: "2584.321", color: "#2B7CE9"}
]);

var container = document.getElementById("mynetwork");
var data = {
  nodes: nodes,
  edges: edges
};

var options = {
  nodes: {
    shape: 'circle',
    size: 30,
    font: {
      size: 16
    },
    borderWidth: 2,
    color: {
      background: '#D2E5FF',
      border: '#2B7CE9'
    }
  },
  edges: {
    width: 2,
    font: {
      size: 12,
      align: 'middle'
    },
    smooth: {
      type: 'continuous'
    }
  },
  physics: {
    enabled: true,
    stabilization: true
  }
};

var network = new vis.Network(container, data, options);

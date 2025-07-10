const { useState, useEffect } = React;

function App() {
  const [tracks, setTracks] = useState([]);
  const [selectedTrack, setSelectedTrack] = useState(null);
  const [editing, setEditing] = useState(false);
  const [formData, setFormData] = useState({});
  const [searchText, setSearchText] = useState("");
  const [error, setError] = useState(null);
  const [showAddForm, setShowAddForm] = useState(false);
  const [newTrack, setNewTrack] = useState({
    trackName: "",
    creator: "",
    description: "",
    createdOn: "",
    publishedOn: ""
  });

  // Fetch tracks from backend
  const fetchTracks = (filter = "") => {
    fetch(`http://localhost:8080/getalltracks?filter=${filter}`)
      .then((res) => res.json())
      .then((data) => setTracks(data))
      .catch(() => setError("Failed to fetch tracks"));
  };

  useEffect(() => {
    fetchTracks();
  }, []);

  // Delete track
  const handleDelete = (trackId) => {
    const confirmDelete = window.confirm("Are you sure you want to delete this track?");
    if (!confirmDelete) return;

    fetch(`http://localhost:8080/deletetrack/${trackId}`, {
      method: "DELETE",
    })
      .then((res) => {
        if (res.ok) {
          alert("Track deleted successfully");
          setTracks((prev) => prev.filter((t) => t.trackId !== trackId));
          setSelectedTrack(null);
        } else {
          alert("Failed to delete track");
        }
      })
      .catch(() => alert("Error deleting track"));
  };

  // Start editing
  const handleEdit = () => {
    setFormData({ ...selectedTrack });
    setEditing(true);
  };

  // Submit updated track
  const handleUpdate = (e) => {
    e.preventDefault();
    fetch(`http://localhost:8080/updatetrack/${selectedTrack.trackId}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(formData),
    })
      .then((res) => {
        if (res.ok) {
          alert("Track updated successfully");
          setEditing(false);
          setSelectedTrack(null);
          fetchTracks();
        } else {
          alert("Failed to update track");
        }
      })
      .catch(() => alert("Error updating track"));
  };

  // Submit new track
  const handleAddTrack = (e) => {
    e.preventDefault();
    fetch("http://localhost:8080/createtrack", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newTrack),
    })
      .then((res) => {
        if (res.ok) {
          alert("Track added successfully!");
          setShowAddForm(false);
          setNewTrack({
            trackName: "",
            creator: "",
            description: "",
            createdOn: "",
            publishedOn: ""
          });
          fetchTracks();
        } else {
          alert("Failed to add track");
        }
      })
      .catch(() => alert("Error while adding track"));
  };

  // Handle search input
  const handleSearch = (e) => {
    const value = e.target.value;
    setSearchText(value);
    fetchTracks(value);
  };

  return (
    <div className="container">
      <h1>Track Manager</h1>

      <input
        type="text"
        placeholder="Search tracks..."
        value={searchText}
        onChange={handleSearch}
      />

      <button onClick={() => setShowAddForm(true)} style={{ margin: "10px" }}>
        Add Track
      </button>

      {error && <p className="error">{error}</p>}

      <ul>
        {tracks.length > 0 ? (
          tracks.map((track) => (
            <li key={track.trackId} onClick={() => setSelectedTrack(track)}>
              {track.trackName}
            </li>
          ))
        ) : (
          <p>No tracks available</p>
        )}
      </ul>

      {/* Track Details View */}
      {selectedTrack && !editing && (
        <div className="track-details">
          <h2>Track Details</h2>
          <p><strong>ID:</strong> {selectedTrack.trackId}</p>
          <p><strong>Name:</strong> {selectedTrack.trackName}</p>
          <p><strong>Creator:</strong> {selectedTrack.creator}</p>
          <p><strong>Description:</strong> {selectedTrack.description}</p>
          <p><strong>Created On:</strong> {selectedTrack.createdOn}</p>
          <p><strong>Published On:</strong> {selectedTrack.publishedOn}</p>

          <button onClick={() => handleDelete(selectedTrack.trackId)}>Delete</button>
          <button onClick={handleEdit} style={{ marginLeft: "10px" }}>Edit</button>
        </div>
      )}

      {/* Edit Form */}
      {editing && (
        <div className="edit-form">
          <h2>Edit Track</h2>
          <form onSubmit={handleUpdate}>
            <input
              type="text"
              placeholder="Track Name"
              value={formData.trackName}
              onChange={(e) => setFormData({ ...formData, trackName: e.target.value })}
              required
            />
            <input
              type="text"
              placeholder="Creator"
              value={formData.creator}
              onChange={(e) => setFormData({ ...formData, creator: e.target.value })}
              required
            />
            <input
              type="text"
              placeholder="Description"
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              required
            />
            <input
              type="date"
              value={formData.createdOn}
              onChange={(e) => setFormData({ ...formData, createdOn: e.target.value })}
              required
            />
            <input
              type="date"
              value={formData.publishedOn}
              onChange={(e) => setFormData({ ...formData, publishedOn: e.target.value })}
              required
            />
            <button type="submit">Update</button>
            <button type="button" onClick={() => setEditing(false)} style={{ marginLeft: "10px" }}>
              Cancel
            </button>
          </form>
        </div>
      )}

      {/* Add Form */}
      {showAddForm && (
        <div className="add-form">
          <h2>Add New Track</h2>
          <form onSubmit={handleAddTrack}>
            <input
              type="text"
              placeholder="Track Name"
              value={newTrack.trackName}
              onChange={(e) => setNewTrack({ ...newTrack, trackName: e.target.value })}
              required
            />
            <input
              type="text"
              placeholder="Creator"
              value={newTrack.creator}
              onChange={(e) => setNewTrack({ ...newTrack, creator: e.target.value })}
              required
            />
            <input
              type="text"
              placeholder="Description"
              value={newTrack.description}
              onChange={(e) => setNewTrack({ ...newTrack, description: e.target.value })}
              required
            />
            <input
              type="date"
              value={newTrack.createdOn}
              onChange={(e) => setNewTrack({ ...newTrack, createdOn: e.target.value })}
              required
            />
            <input
              type="date"
              value={newTrack.publishedOn}
              onChange={(e) => setNewTrack({ ...newTrack, publishedOn: e.target.value })}
              required
            />
            <button type="submit">Submit</button>
            <button type="button" onClick={() => setShowAddForm(false)} style={{ marginLeft: "10px" }}>
              Cancel
            </button>
          </form>
        </div>
      )}
    </div>
  );
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<App />);

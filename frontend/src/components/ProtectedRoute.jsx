import { Navigate, useLocation } from "react-router-dom";

function ProtectedRoute({ children }) {
  const token = localStorage.getItem("token");
  const location = useLocation();

  if (!token) {
    // not logged in → redirect to login, and remember where user wanted to go
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  // logged in → show protected content
  return children;
}

export default ProtectedRoute;

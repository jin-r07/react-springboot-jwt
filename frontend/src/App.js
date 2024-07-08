import { Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import Navbar from './components/common/Navbar';
import LoginPage from './components/auth/LoginPage';
import ProfilePage from './components/user/ProfilePage';
import RegistrationPage from './components/auth/RegistrationPage';
import UserManagementPage from './components/user/UserManagementPage';
import UpdateUser from './components/user/UpdateUser';
import UserService from './components/service/UserService';
import Home from './components/common/Home';

function App() {
  return (
  <div className="App">
        <Navbar />
        <div className="content">
          <Routes>
            <Route exact path="/" element={<Home />} />
            <Route exact path="/login" element={<LoginPage />} />
            <Route path="/profile" element={<ProfilePage />} />

            {UserService.adminOnly() && (
              <>
                <Route path="/register" element={<RegistrationPage />} />
                <Route path="/admin/user-management" element={<UserManagementPage />} />
                <Route path="/update-user/:userId" element={<UpdateUser />} />
              </>
            )}
            <Route path="*" element={<Navigate to="/login" />} />‰
          </Routes>
        </div>
      </div>
  );
}

export default App;
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import HomePage from 'pages/HomePage';
import ProductPage from 'pages/ProductPage';

function App() {
	return (
		<Router>
			<Routes>
				<Route path='/' element={<HomePage />} />
				<Route path='/product/:productId' element={<ProductPage />} />
			</Routes>
		</Router>
	);
}

export default App;

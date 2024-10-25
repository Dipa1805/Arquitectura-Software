-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-11-2022 a las 21:22:07
-- Versión del servidor: 10.4.8-MariaDB
-- Versión de PHP: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistema`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumnos`
--

CREATE TABLE `alumnos` (
  `id_alumno` int(11) NOT NULL,
  `nombre` varchar(253) NOT NULL,
  `apellido` varchar(253) NOT NULL,
  `grado` varchar(253) NOT NULL,
  `seccion` varchar(9) NOT NULL,
  `id_curso_asignado` varchar(253) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `alumnos`
--

INSERT INTO `alumnos` (`id_alumno`, `nombre`, `apellido`, `grado`, `seccion`, `id_curso_asignado`) VALUES
(1, 'Efrain', 'AYUQUE CCENCHO ', '3ro', 'A', 'Arte y cultura'),
(2, 'Eddy ', 'CARHUALLANQUI  PALIAN', '3ro', 'A', 'Arte y cultura'),
(3, 'Max Linder', 'DE LA CRUZ TAIPE Max ', '3ro', 'A', 'Arte y cultura'),
(4, ' Yonyl', 'ESPINOZA CONDORI ', '3ro', 'A', 'Arte y cultura'),
(5, ' Yuly', 'FELIX CARDENAS ', '3ro', 'A', 'Arte y cultura'),
(6, ' Jose Angel', 'HUINCHO HUILLCAS', 'Seleccione Grado', 'A', 'Seleccione Materia'),
(7, ' Sandra Sumi', 'JACOBE QUISURUCO ', '3ro', 'A', 'Arte y cultura'),
(8, ' Mariana Araceli', 'ALIAGA ZARATE', '3ro', 'B', 'Arte y cultura'),
(9, 'Edith M.', 'ANTEZANA RAMIREZ  ', '3ro', 'B', 'Arte y cultura'),
(10, 'Celestino', 'BARBOZA ACU?A', '3ro', 'B', 'Arte y cultura'),
(11, 'Mery Pamela ', 'CAJA ROMO Mery ', '3ro', 'B', 'Arte y cultura'),
(12, 'Alexia Dianira ', 'CHANCA MARALLANO', '3ro', 'B', 'Arte y cultura'),
(13, ' Kengiu Sebastian', 'CHAVEZ FLORES ', '3ro', 'B', 'Arte y cultura'),
(14, 'Lucio Velasco', 'ALVARADO HUAMAN', '3ro', 'C', 'Arte y cultura'),
(15, 'Fernandito Victor', 'ASTO GAVILAN', '3ro', 'C', 'Arte y cultura'),
(16, 'Omar Anderson', 'BOZA CASTILLO', '3ro', 'C', 'Arte y cultura'),
(17, 'Yadira Estrella', 'CAPACYACHI QUISPE', '3ro', 'C', 'Arte y cultura'),
(18, 'Anderson Franco', 'CAPCHA CAPCHA', '3ro', 'C', 'Arte y cultura'),
(19, 'Helen G.', 'CAPCHA CUYCAPUZA', '3ro', 'C', 'Arte y cultura'),
(20, 'Jean Pool', 'CAPCHA PAUCAR', '3ro', 'D', 'Arte y cultura'),
(21, 'Celeste Sarita', 'CCANTO TORRES', '3ro', 'D', 'Arte y cultura'),
(22, ' Yerson David', 'ENRIQUEZ CCENCHO', '3ro', 'D', 'Arte y cultura'),
(23, 'Andy Jhoel', 'ESCOBAR ORONCUY', '3ro', 'D', 'Arte y cultura'),
(24, 'Jean Maycol', 'FERNANDEZ PAITAN', '3ro', 'D', 'Arte y cultura'),
(25, 'Mirella ', 'FLORES MENDOZA', '3ro', 'D', 'Arte y cultura');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `curso`
--

CREATE TABLE `curso` (
  `id_curso` int(11) NOT NULL,
  `nombre_curso` varchar(253) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `curso`
--

INSERT INTO `curso` (`id_curso`, `nombre_curso`) VALUES
(1, 'matematica'),
(2, 'Ingles'),
(3, 'Comunicacion'),
(4, 'Religion'),
(5, 'Arte y Cultura'),
(6, 'EPT'),
(7, 'Ciencia y Tecnologia'),
(8, 'DPCC'),
(9, 'Ed. Fisica'),
(10, 'Ciencias Sociales');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(253) NOT NULL,
  `email` varchar(253) NOT NULL,
  `telefono` varchar(253) NOT NULL,
  `username` varchar(253) NOT NULL,
  `password` varchar(253) NOT NULL,
  `tipo_nivel` varchar(253) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre`, `email`, `telefono`, `username`, `password`, `tipo_nivel`) VALUES
(1, 'jhulinho', 'escobar123@gmail.com', '929656998', 'admin', 'admin', 'Administrador'),
(2, 'diego', 'diego@gmail.com', '965231478', 'hina misora', 'hinagod', 'Administrador'),
(3, 'richard', 'richard@gmail.com', '999184306', 'richard', 'richard', 'Administrador');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumnos`
--
ALTER TABLE `alumnos`
  ADD PRIMARY KEY (`id_alumno`);

--
-- Indices de la tabla `curso`
--
ALTER TABLE `curso`
  ADD PRIMARY KEY (`id_curso`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alumnos`
--
ALTER TABLE `alumnos`
  MODIFY `id_alumno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT de la tabla `curso`
--
ALTER TABLE `curso`
  MODIFY `id_curso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

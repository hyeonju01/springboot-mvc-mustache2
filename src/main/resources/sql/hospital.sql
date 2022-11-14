SELECT road_name_address, hospital_name FROM nation_wide_hospitals
WHERE road_name_address like '경기도 수원시%';

-- 보건소, 보건지소 찾기
SELECT * FROM nation_wide_hospitals WHERE business_type_name like '보건%';
-- 3450 Rows

SELECT * FROM nation_wide_hospitals WHERE business_type_name in ('보건소', '보건지소');
-- 3435 Rows
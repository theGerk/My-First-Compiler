{use all nodes!}
program ID;
var fibs : array[-2 : 4] of integer;
function factorial(a : integer) : integer;
	begin
		if a < 1 then factorial := 1 else factorial := a * factorial(a - fibs[-2])
	end;
procedure populate;
	var iter : integer;
	function populate(a : integer): integer;
		begin
			populate := factorial(a)
		end;
	begin
		iter := 0;
		read(fibs[-2]);
		read(fibs[-1]);
		while iter <= 4 do
			begin
				fibs[iter] := populate(fibs[iter - 1]);
				iter := iter + 1
			end
	end;
procedure r;
	var iter : integer;
	procedure r1;
		begin
			write(fibs[iter]);
			iter := iter + 1
		end;
	begin
		iter := -2;
		while iter <= 4 do r1
	end;
begin
	populate;
	r
end
.

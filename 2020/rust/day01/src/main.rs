use std::env;
use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

const TARGET: u32 = 2020;

fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
where
    P: AsRef<Path>,
{
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}

fn read_file_to_integers(filename: &String) -> Vec<u32> {
    let mut nums: Vec<u32> = Vec::new();
    if let Ok(lines) = read_lines(filename) {
        for line in lines {
            if let Ok(ip) = line {
                let num: u32 = ip.parse().unwrap();
                nums.push(num);
            }
        }
    }
    return nums;
}

fn main() {
    let args: Vec<String> = env::args().collect();
    if args.len() != 2 {
        println!("Give input file as param");
        return;
    }
    let my_nums = read_file_to_integers(&args[1]);

    for i in &my_nums {
        for j in &my_nums[1..] {
            if i + j == TARGET {
                let multiplication = i * j;
                println!(
                    "{0} + {1} = {2}, {0} * {1} = {3}",
                    i, j, TARGET, multiplication
                );
                return;
            }
        }
    }
}

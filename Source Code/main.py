import os 

def make_commit(days: int):
    if(days < 1):
        #Push
        return os.system('git push origin IO')
    else:
        dates = f'{days} hours ago'

        with open('bot.txt','a') as file:
            file.write(f'{dates}\n')

        #Staging
        os.system('git add .')

        #Commit
        os.system('git commit --date="'+ dates +'" -m "-v IO"')              

        return days * make_commit(days-1)

make_commit(8)        